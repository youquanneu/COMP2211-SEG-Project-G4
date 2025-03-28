package com.campus.Service.User;

public class UserServiceTest {
//
//    @Override
//    public void run(String... args) throws Exception {
//        login();
//    }
//    @Autowired
//    private UserService userService;
//    @Test
//    public void login(){
//        String username = "user1";
//        String password = "password1";
//        try {
//            User user = userService.loginByUsername(username, password);
//            System.out.println(user);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//    }   // Demonstration method: Login as user by username and password
//
//    public void changePassword(){
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Input username : ");
//        String username = scanner.nextLine();
//        System.out.println("Input password : ");
//        String password = scanner.nextLine();
//        try {
//            changePassword(userService.loginByUsername(username, password));
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//            changePassword();
//        }
//    }   // Demonstration method: Change password
//    private void changePassword(User user) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter your current password: ");
//        String password = scanner.nextLine();
//        System.out.println("New password: ");
//        String newPassword = scanner.nextLine();
//        System.out.println("Confirm password: ");
//        String confirmationPassword = scanner.nextLine();
//        try{
//            userService.changePassword(user,password,newPassword,confirmationPassword);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//            changePassword(user);
//        }
//    }   // Demonstration method: Change password of the user
//    public void forgotPasswordDemo(){
//        try {
//            // Page 1 : Take email and send OTP
//            Scanner scanner = new Scanner(System.in);
//            System.out.println("Your email : ");
//            String email = scanner.nextLine();
//            // Page 2 : Get user input of OTP
//            System.out.println("Your OTP : ");
//            String inputOTP = scanner.nextLine();
//            User user = userService.forgotPassword(email,inputOTP);
//            // Page 3 : Let user change of password
//            System.out.println("New Password : ");
//            String newPassword = scanner.nextLine();
//            System.out.println("Confirm Password : ");
//            String confirmationPassword = scanner.nextLine();
//            userService.changeToNewPassword(user,newPassword,confirmationPassword);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//            forgotPasswordDemo();
//        }
//    }   // Demonstration method: Forgot password
}
