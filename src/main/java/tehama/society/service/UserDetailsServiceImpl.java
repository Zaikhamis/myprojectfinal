package tehama.society.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tehama.society.model.User;
import tehama.society.model.UserPrincipal;
import tehama.society.repository.UserRepository;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    //    login
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User loginUser = userRepository.findUserByUsername(username);
        if (loginUser == null) {
            throw new RuntimeException("User by username: " + username + " not found");
        }

        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        log.info("User found by username: " + username);
        return userPrincipal;
    }

}
