package tehama.society.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tehama.society.constant.FileConstant;
import tehama.society.model.Branch;
import tehama.society.model.Student;
import tehama.society.model.Teacher;
import tehama.society.model.User;
import tehama.society.repository.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BranchService branchService;
    private final EmailService emailService;


    public Student registerNewStudent(Student student, long branchId) {
        validateNewUsernameAndEmail(null, student.getUsername(), student.getEmail());
        Branch branch = branchService.findBranch(branchId);
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setRole("ROLE_STUDENT");
        student.setActive(true);
        student.setNotLocked(true);
        student.setBranch(branch);
        log.info("New student registered.");
        userRepository.save(student);
        return student;
    }


    public Student registerNewStudent(String firstName, String lastName, String username, String password, String email,
                                      String phoneNumber, String dateOfBirth, String gender, String address, String regNumber, long branchId) {
        validateNewUsernameAndEmail(null, username, email);
        Student student = new Student();
        Branch branch = branchService.findBranch(branchId);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setUsername(username);
        student.setPassword(passwordEncoder.encode(password));
        student.setEmail(email);
        student.setPhoneNumber(phoneNumber);
        student.setDateOfBirth(dateOfBirth);
        student.setGender(gender);
        student.setAddress(address);
        student.setRole("ROLE_STUDENT");
        student.setActive(true);
        student.setNotLocked(true);
        student.setRegNumber(regNumber);
        student.setBranch(branch);
        log.info("New student with branch registered.");
        userRepository.save(student);
        emailService.sendNewAccountMessage(firstName, email);
        log.info("");
        return student;
    }



    public Teacher registerNewTeacher(Teacher teacher) {
        validateNewUsernameAndEmail(null, teacher.getUsername(), teacher.getEmail());
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        teacher.setRole("ROLE_TEACHER");
        teacher.setActive(true);
        teacher.setNotLocked(true);
        log.info("New teacher registered.");
        userRepository.save(teacher);
        return teacher;
    }


    public User addNewUser(User user) {
        validateNewUsernameAndEmail(null, user.getUsername(), user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setNotLocked(true);
        log.info("New user added.");
        userRepository.save(user);
        return user;
    }


    //    UPDATE USER
    public User updateUser(String currentUsername, String firstName, String lastName, String username,
                           String email, String phoneNumber, String dateOfBirth,
                           String gender, String address, String role, boolean isActive, boolean isNotLocked, MultipartFile profileImage) throws IOException {
        User updateUser = validateNewUsernameAndEmail(currentUsername, username, email);
        assert updateUser != null;
        updateUser.setFirstName(firstName);
        updateUser.setLastName(lastName);
        updateUser.setUsername(username);
        updateUser.setEmail(email);
        updateUser.setPhoneNumber(phoneNumber);
        updateUser.setDateOfBirth(dateOfBirth);
        updateUser.setGender(gender);
        updateUser.setAddress(address);
        updateUser.setRole(role);
        updateUser.setActive(isActive);
        updateUser.setNotLocked(isNotLocked);
        log.info("User updated successfully.");
        userRepository.save(updateUser);
        saveProfileImage(updateUser, profileImage);
        return updateUser;
    }


    //    UPDATE STUDENT
    public Student updateStudent(String currentUsername, String firstName, String lastName, String username,
                                 String email, String phoneNumber, String dateOfBirth,
                                 String gender, String address, boolean isActive, boolean isNotLocked, String regNumber) {
        Student updateStudent = (Student) validateNewUsernameAndEmail(currentUsername, username, email);
        assert updateStudent != null;
        updateStudent.setFirstName(firstName);
        updateStudent.setLastName(lastName);
        updateStudent.setUsername(username);
        updateStudent.setEmail(email);
        updateStudent.setPhoneNumber(phoneNumber);
        updateStudent.setDateOfBirth(dateOfBirth);
        updateStudent.setGender(gender);
        updateStudent.setAddress(address);
        updateStudent.setActive(isActive);
        updateStudent.setNotLocked(isNotLocked);
        updateStudent.setRegNumber(regNumber);
        log.info("Student updated successfully.");
        userRepository.save(updateStudent);
        return updateStudent;
    }


    //    UPDATE STUDENT
    public Teacher updateTeacher(String currentUsername, String firstName, String lastName, String username,
                                 String email, String phoneNumber, String dateOfBirth,
                                 String gender, String address, boolean isActive, boolean isNotLocked, String employeeID, String department) {
        Teacher updateTeacher = (Teacher) validateNewUsernameAndEmail(currentUsername, username, email);
        assert updateTeacher != null;
        updateTeacher.setFirstName(firstName);
        updateTeacher.setLastName(lastName);
        updateTeacher.setUsername(username);
        updateTeacher.setEmail(email);
        updateTeacher.setPhoneNumber(phoneNumber);
        updateTeacher.setDateOfBirth(dateOfBirth);
        updateTeacher.setGender(gender);
        updateTeacher.setAddress(address);
        updateTeacher.setActive(isActive);
        updateTeacher.setNotLocked(isNotLocked);
        updateTeacher.setEmployeeID(employeeID);
        updateTeacher.setDepartment(department);
        log.info("Teacher updated successfully.");
        userRepository.save(updateTeacher);
        return updateTeacher;
    }


    //    DELETE USER
    public void deleteUser(long userId) {
        User user = userRepository.findUserByUserId(userId);
        if (user == null) {
            throw new RuntimeException("User by ID: " + userId + " not found");
        }

        userRepository.deleteById(userId);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public List<Student> getAllStudentsByCourseName(String courseName) {
        return userRepository.getAllStudentsByCourseName(courseName);
    }


    public List<User> getAllTeachers() {
        return userRepository.getAllTeachers();
    }


    //    findUserByUsername
    public User findUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new RuntimeException("User by username: " + username + " not found");
        }

        return user;
    }


    //    findUserByUserId
    public User findUserByUserId(long userId) {
        User user = userRepository.findUserByUserId(userId);
        if (user == null) {
            throw new RuntimeException("User by ID: " + userId + " not found");
        }

        return user;
    }


    //    SAVE PROFILE IMAGE
    private void saveProfileImage(User user, MultipartFile profileImage) throws IOException {
        if (profileImage != null) {
            Path userFolder = Paths.get(FileConstant.USER_FOLDER + user.getUsername()).toAbsolutePath().normalize();
            if (!Files.exists(userFolder)) {
                Files.createDirectories(userFolder);
                log.info("Directory created for: " + userFolder);
            }

            Files.deleteIfExists(Paths.get(FileConstant.USER_FOLDER + user.getUsername() + "." + "jpg"));
            Files.copy(profileImage.getInputStream(), userFolder.resolve(user.getUsername() + "." + "jpg"), REPLACE_EXISTING);
            user.setProfileImage(setProfileImage(user.getUsername()));
            userRepository.save(user);
            log.info(profileImage.getOriginalFilename());
        }
    }

    private String setProfileImage(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(FileConstant.USER_IMAGE_PATH + username + "/" + username + "." + "jpg").toUriString();
    }


    //    updateProfileImage
    public User updateProfileImage(String username, MultipartFile profileImage) throws IOException {
        User user = validateNewUsernameAndEmail(username, null, null);
        saveProfileImage(user, profileImage);
        return user;
    }


    //  VALIDATE NEW USERNAME AND EMAIL
    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) {
        User userByNewUsername = userRepository.findUserByUsername(newUsername);
        User userByNewEmail = userRepository.findUserByEmail(newEmail);
        if (currentUsername != null) {

            User currentUser = userRepository.findUserByUsername(currentUsername);
            if (currentUser == null) {
                log.error("NO_USER_FOUND_BY_USERNAME " + currentUsername);
                throw new RuntimeException("NO_USER_FOUND_BY_USERNAME " + currentUsername);
            }

            if (userByNewUsername != null && !currentUser.getUserId().equals(userByNewUsername.getUserId())) {
                log.error("USERNAME_ALREADY_EXISTS " + userByNewUsername);
                throw new RuntimeException("USERNAME_ALREADY_EXISTS" + userByNewUsername);
            }

            if (userByNewEmail != null && !currentUser.getUserId().equals(userByNewEmail.getUserId())) {
                log.error("EMAIL_ALREADY_EXISTS " + userByNewEmail);
                throw new RuntimeException("EMAIL_ALREADY_EXISTS " + userByNewEmail);
            }

            return currentUser;

        } else {

            if (userByNewUsername != null) {
                log.error("USERNAME_ALREADY_EXISTS " + userByNewUsername);
                throw new RuntimeException("USERNAME_ALREADY_EXISTS " + userByNewUsername);
            }

            if (userByNewEmail != null) {
                log.error("EMAIL_ALREADY_EXISTS " + userByNewEmail);
                throw new RuntimeException("EMAIL_ALREADY_EXISTS " + userByNewEmail);
            }

            return null;

        }
    }


    public Integer getTotalUsers() {
        return userRepository.getTotalUsers();
    }

    public Integer getTotalTeachers() {
        return userRepository.getTotalTeachers();
    }

    public Integer getTotalStudents() {
        return userRepository.getTotalStudents();
    }
}
