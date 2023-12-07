import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class buoi1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("-----------------MENU-----------------");
            System.out.println("1. Kiem tra chan le.");
            System.out.println("2. Kiem tra nam nhuan.");
            System.out.println("3. Tinh so ngay cua mot thang.");
            System.out.println("4. Hien thi thu trong tuan.");
            System.out.println("5. Viet hoa mot chuoi.");
            System.out.println("6. Dao nguoc chuoi.");
            System.out.println("7. In ra cac so nguyen to <= n.");
            System.out.println("0. Thoat chuong trinh.");            
            System.out.println("Chon mot so tu 1 den 7 hoac nhap 0 de thoat:");
            while (!scanner.hasNextInt()) {
                System.out.println("Vui long nhap mot so nguyen.");
                scanner.next();
            }
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    EvenOddChecker();
                    break;
                case 2:
                    kiemTraNamNhuan();
                    break;
                case 3:
                    songaytrongthang();
                    break;
                case 4:
                    trathuvatuan();
                    break;
                case 5:
                    viethoa();
                    break;
                case 6:
                    daochuoi();
                    break;
                case 7:
                    timsonguyento();
                    break;
                case 0:
                    System.out.println("--------------------------------");
                    System.out.println("Thoat chuong trinh.");
                    break;
                default:
                    System.out.println("Lua chon khong hop le. Vui long chon tu 1 den 7 hoac nhap 0 de thoat.");
            }
        } while (choice != 0);

        scanner.close();
    }

    public static void EvenOddChecker() {
        Scanner scanner = new Scanner(System.in);
        int number;

        
            System.out.print("Nhap mot so nguyen: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Vui long chi nhap so nguyen.");
                scanner.next();
            }
            number = scanner.nextInt();

        System.out.println("--------------------------------");
            if (number % 2 == 0) {
                System.out.println(number + " la so chan.");
            } else {
                System.out.println(number + " la so le.");
            }
        System.out.println("----------------------------------------------------");
    }

    public static void kiemTraNamNhuan() {
        Scanner scanner = new Scanner(System.in);
        int year;

        System.out.print("Nhap mot nam de kiem tra xem no co phai la nam nhuan hay khong: ");

        while (!scanner.hasNextInt()) {
            System.out.println("Vui long chi nhap mot so.");
            scanner.next();
        }
        year = scanner.nextInt();

        System.out.println("--------------------------------");
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            System.out.println(year + " la nam nhuan.");
        } else {
            System.out.println(year + " khong phai la nam nhuan.");
        }
         System.out.println("----------------------------------------------------");

    }

    public static void songaytrongthang() {
        Scanner scanner = new Scanner(System.in);

        int month, year;
        int daysInMonth = -1;

        do {
            System.out.print("Nhập tháng (1-12): ");
            month = scanner.nextInt();

            System.out.print("Nhập năm: ");
            year = scanner.nextInt();

            if (month < 1 || month > 12 || year <= 0 ) {
                System.out.println("Tháng hoặc năm không hợp lệ. Vui lòng nhập lại.");
            }
        } while (month < 1 || month > 12 || year <= 0);

        System.out.println("--------------------------------");
        if (kiemTraRangBuoc(month, year)) {
            switch (month) {
                case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                    daysInMonth = 31;
                    break;
                case 4: case 6: case 9: case 11:
                    daysInMonth = 30;
                    break;
                case 2:
                    daysInMonth = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0) ? 29 : 28;
                    break;
                default:
                    daysInMonth = -1; // Trường hợp mặc định (không xảy ra)
            }

            System.out.println("Tháng " + month + " năm " + year + " có " + daysInMonth + " ngày.");
        } else {
            System.out.println("Tháng không hợp lệ.");
        }
    }

    public static boolean kiemTraRangBuoc(int month, int year) {
        return (month >= 1 && month <= 12 && year > 0);
    }
  
    public static void trathuvatuan() {
        Scanner scanner = new Scanner(System.in);

        int day;
        do {
            System.out.print("Nhap so ngay (1-366): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Vui long nhap mot so nguyen.");
                scanner.next(); // Bỏ qua giá trị không phải số nguyên
            }
            day = scanner.nextInt();
        } while (day <= 0 || day > 366);

        int year;
        do {
            System.out.print("Nhap nam: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Vui long nhap mot so nguyen.");
                scanner.next(); // Bỏ qua giá trị không phải số nguyên
            }
            year = scanner.nextInt();
        } while (year <= 0);

        try {
            String dateString = year + "-01-01"; // Giả sử là ngày đầu tiên của năm
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateString);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, day - 1); // Trừ đi 1 vì ngày 1 tháng 1 là ngày thứ 1

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);

            System.out.println("--------------------------------");
            System.out.println("Ngay " + day + " nam " + year + " la thu " + dayOfWeek + " trong tuan.");
            System.out.println("Ngay " + day + " nam " + year + " thuoc tuan thu " + weekOfYear + " cua nam.");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public static void viethoa() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Nhap vao mot chuoi: ");
        String inputString = scanner.nextLine();

        String resultString = inputString.toUpperCase();

        System.out.println("--------------------------------");
        System.out.println("Chuoi sau khi chuyen doi thanh chu in hoa: " + resultString);
    }
  
    public static void daochuoi() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Nhap vao mot chuoi: ");
        String inputString = scanner.nextLine();

        StringBuilder reversedStringBuilder = new StringBuilder(inputString);
        reversedStringBuilder.reverse();
        String resultString = reversedStringBuilder.toString();
        System.out.println("--------------------------------");
        System.out.println("Chuoi truoc khi dao nguoc: " +inputString);
        System.out.println("Chuoi sau khi dao nguoc: " + resultString);
    }
    
   public static void timsonguyento() {
    Scanner scanner = new Scanner(System.in);

    int n;

    do {
        System.out.print("Nhap vao mot so nguyen duong: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Vui long nhap mot so nguyen duong.");
            scanner.next();
        }
        n = scanner.nextInt();

        if (n <= 0) {
            System.out.println("Vui long nhap mot so nguyen duong lon hon 0.");
        }
    } while (n <= 0);

    System.out.println("Cac so nguyen to nho hon hoac bang " + n + " la:");

    for (int i = 2; i <= n; i++) {
        boolean isPrime = true;
        if (i < 2) {
            isPrime = false;
        } else {
            for (int j = 2; j <= Math.sqrt(i); j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
        }

        if (isPrime) {
            System.out.print(i + " ");
        }


    }   System.out.print("\n"); // Xuống dòng mới     
}
}


