   import java.util.*;

class Calendar {
   static int [] months= {31,28,31,30,31,30,31,31,30,31,30,31};

   public static String convertDate(String date){
      String converted = "";
      for (int i = 0; i<date.length();i++){
         if (date.charAt(i) == '-'){
            converted += ' ';
         }
         else{
            converted += date.charAt(i);
         }
      }
      Scanner scanner = new Scanner(converted);
      int year = scanner.nextInt();
      int month = scanner.nextInt();
      int day = scanner.nextInt();
      System.out.println(day + " " + month + " " + year);
      return day + " " + month + " " + year;
   }


   
   public static String checkOutDate(String checkIn, int lengthOfVisit){ //takes a check-in date and a time period and returns the last day of the visit
      for (int i=0;i<lengthOfVisit;i++){
         checkIn=nextDay(checkIn);
      }
      return checkIn;
   }
   


   
   public static String nextDay(String startDate){ //return the next day
      Scanner scanner= new Scanner(startDate);
      int startDay=scanner.nextInt();
      int startMonth=scanner.nextInt();
      int startYear=scanner.nextInt();;
      if (isLeapYear(startYear)){
         months[1]=29;
      }
      else{
         months[1]=28;
      }
      if (startDay<months[startMonth-1]){
         startDay++;
      }
      else if(startMonth<12){
         startDay=1;
         startMonth++;
      }
      else{
         startYear++;
         startMonth=1;
         startDay=1;
      }
      return(startDay +" "+startMonth+" "+startYear);
   }
   
   public static int daysInBetween(String startDate, String endDate){ //day counter to know how many days we have booked to.
      int count=1;
      while (!startDate.equalsIgnoreCase(endDate)){
         count++;
         startDate=nextDay(startDate);
      }
      return count;
   }
   
   public static boolean isLeapYear(int year){ //check if it is a leap year
      if(year%4!=0){
         return false;
      }
      else if(year%100!=0){
         return true;
      }
      else if(year%400!=0){
         return false;
      }
      else{
         return true;
      }
   }
}