/**
 *      Author: Douglas T Kadzutu
 *      Date: 22/03/2023
 *      Description: This class has helper methods that ar used across different components
 */
package Helper;

public class Utils {
    public static boolean validateEmail(String email){
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; // email pattern
        return email.matches(regex);
    }

    public static boolean validateContact(String contactNo){
        // 0871234561
        String regex = "^[0-9]{1,15}$"; // 15 digits max
        return contactNo.matches(regex);
    }

    public static boolean validateUrl(String website){
        // www.example.com
        String regex = "^(https?://)?[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(/[a-zA-Z0-9-]+)*$";
        return website.matches(regex);
    }

    public static boolean validateName(String name) {
        //String
        String regex = "^[a-zA-Z\\s'-]+$";
        return name.matches(regex);
    }

    public  static boolean validatePrice(String price) {
        //Double
        String regex = "^\\d+(\\.\\d{1,2})?$";
        return price.matches(regex);
    }

    public static boolean validateDate(String date) {
        //YYYY-MM-DD
        String regex = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
        return date.matches(regex);
    }
}
