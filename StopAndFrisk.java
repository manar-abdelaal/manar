import java.util.ArrayList;

/**
 * The StopAndFrisk class represents stop-and-frisk data, provided by
 * the New York Police Department (NYPD), that is used to compare
 * during when the policy was put in place and after the policy ended.
 * 
 * @author Tanvi Yamarthy
 * @author Vidushi Jindal
 */
public class StopAndFrisk {

    /*
     * The ArrayList keeps track of years that are loaded from CSV data file.
     * Each SFYear corresponds to 1 year of SFRecords. 
     * Each SFRecord corresponds to one stop and frisk occurrence.
     */ 
    private ArrayList<SFYear> database; 

    /*
     * Constructor creates and initializes the @database array
     * 
     * DO NOT update nor remove this constructor
     */
    public StopAndFrisk () {
        database = new ArrayList<>();
    }

    /*
     * Getter method for the database.
     * *** DO NOT REMOVE nor update this method ****
     */
    public ArrayList<SFYear> getDatabase() {
        return database;
    }

    /**
     * This method reads the records information from an input csv file and populates 
     * the database.
     * 
     * Each stop and frisk record is a line in the input csv file.
     * 
     * 1. Open file utilizing StdIn.setFile(csvFile)
     * 2. While the input still contains lines:
     *    - Read a record line (see assignment description on how to do this)
     *    - Create an object of type SFRecord containing the record information
     *    - If the record's year has already is present in the database:
     *        - Add the SFRecord to the year's records
     *    - If the record's year is not present in the database:
     *        - Create a new SFYear 
     *        - Add the SFRecord to the new SFYear
     *        - Add the new SFYear to the database ArrayList
     * 
     * @param csvFile
     */
    public void readFile ( String csvFile ) {

        // DO NOT remove these two lines
        StdIn.setFile(csvFile); // Opens the file
        StdIn.readLine();       // Reads and discards the header line
        while (StdIn.hasNextLine()){
            String line = StdIn.readLine();
            String[] values = line.split(",");
           int year = Integer.parseInt(values[0]);
           String description = values[2];
           String gender = values[52];
           String race = values[66];
           String location = values[71];
           boolean arrested = values[13].equals("Y");
           boolean frisked = values[16].equals("Y");

           SFRecord record = new SFRecord(description, arrested, frisked, gender, race, location );
           boolean yearFound = false;
           for (int i = 0; i<database.size(); i++){
            SFYear yearRecord=database.get(i);
            if (yearRecord.getcurrentYear() == year){
                yearRecord.addRecord(record);
                yearFound = true;
                break;
            }
           }
           if (!yearFound){
                SFYear newYearRecord = new SFYear(year);
                newYearRecord.addRecord(record);
                database.add(newYearRecord);
           }

        }
    }

    /**
     * This method returns the stop and frisk records of a given year where 
     * the people that was stopped was of the specified race.
     * 
     * @param year we are only interested in the records of year.
     * @param race we are only interested in the records of stops of people of race. 
     * @return an ArrayList containing all stop and frisk records for people of the 
     * parameters race and year.
     */

    public ArrayList<SFRecord> populationStopped ( int year, String race ) {
        ArrayList<SFRecord> raceRecords = new ArrayList<>();
        for (int i=0; i<database.size();i++){
            SFYear currentYear = database.get(i);
            if (currentYear.getcurrentYear()== year){
                for (int j =0; j<currentYear.getRecordsForYear().size(); j++){
                    if (currentYear.getRecordsForYear().get(j).getRace().equals(race)){
                        raceRecords.add(currentYear.getRecordsForYear().get(j));
                    }
                } break;
            } 
        }
        return raceRecords;
    }

    /**
     * This method computes the percentage of records where the person was frisked and the
     * percentage of records where the person was arrested.
     * 
     * @param year we are only interested in the records of year.
     * @return the percent of the population that were frisked and the percent that
     *         were arrested.
     */
    public double[] friskedVSArrested ( int year ) {
        double[] fvaPercentage = new double[2];
        double friskedCount = 0;
        double friskedPercent =0;
        double arrestedCount = 0;
        double arrestedPercent=0;
         for (int i=0; i<database.size();i++){
            SFYear currentYear = database.get(i);
            if (currentYear.getcurrentYear()== year){
                for (int j =0; j<currentYear.getRecordsForYear().size(); j++){
                    if (currentYear.getRecordsForYear().get(j).getFrisked()){
                        friskedCount++;
                    }
                    if (currentYear.getRecordsForYear().get(j).getArrested()){
                        arrestedCount++;
                    }
                }
                friskedPercent = (friskedCount/currentYear.getRecordsForYear().size())*100;
                arrestedPercent = (arrestedCount/currentYear.getRecordsForYear().size())*100;
                break;
            }
        }
        fvaPercentage[0]=friskedPercent;
        fvaPercentage[1]=arrestedPercent;
        return fvaPercentage; 
    }

    /**
     * This method keeps track of the fraction of Black females, Black males,
     * White females and White males that were stopped for any reason.
     * Drawing out the exact table helps visualize the gender bias.
     * 
     * @param year we are only interested in the records of year.
     * @return a 2D array of percent of number of White and Black females
     *         versus the number of White and Black males.
     */
    public double[][] genderBias ( int year ) {
        double blackMenCount=0;
        double blackMenPercent =0;
        double blackWomenCount=0;
        double blackWomenPercent=0;
        double whiteMenCount=0;
        double whiteMenPercent=0;
        double whiteWomenCount=0;
        double whiteWomenPercent=0;
        double blackPeopleCount=0;
        double whitePeopleCount =0;
        double[][] percents = new double[2][3];
        for (int i=0; i<database.size();i++){
            SFYear currentYear = database.get(i);
            if (currentYear.getcurrentYear()== year){
                 for (int j =0; j<currentYear.getRecordsForYear().size(); j++){
                    if (currentYear.getRecordsForYear().get(j).getRace().equals("B")){
                         if (currentYear.getRecordsForYear().get(j).getGender().equals("M")){
                            blackMenCount++;
                        } else if (currentYear.getRecordsForYear().get(j).getGender().equals("F")){
                            blackWomenCount++;
                        } 
                        blackPeopleCount++;
                    } else if (currentYear.getRecordsForYear().get(j).getRace().equals("W")){
                        if (currentYear.getRecordsForYear().get(j).getGender().equals("M")){
                            whiteMenCount++;
                        } else if (currentYear.getRecordsForYear().get(j).getGender().equals("F")){
                            whiteWomenCount++;
                        } 
                        whitePeopleCount++;
                    }
                 }
                 blackMenPercent=(blackMenCount/(blackPeopleCount))* 0.5 * 100;
                 blackWomenPercent=(blackWomenCount/(blackPeopleCount))* 0.5 * 100;
                 whiteMenPercent=(whiteMenCount/(whitePeopleCount))* 0.5 * 100;
                 whiteWomenPercent=(whiteWomenCount/(whitePeopleCount))* 0.5 * 100;
                 percents[0][0] = blackWomenPercent;
                 percents[1][0] = blackMenPercent;
                 percents[0][1] = whiteWomenPercent;
                 percents[1][1] = whiteMenPercent;
                 percents[0][2] = blackWomenPercent + whiteWomenPercent;
                 percents[1][2]= blackMenPercent + whiteMenPercent;

                break;

            }
        }

        return percents; 
    }

    /**
     * This method checks to see if there has been increase or decrease 
     * in a certain crime from year 1 to year 2.
     * 
     * Expect year1 to preceed year2 or be equal.
     * 
     * @param crimeDescription
     * @param year1 first year to compare.
     * @param year2 second year to compare.
     * @return 
     */

    public double crimeIncrease ( String crimeDescription, int year1, int year2 ) {
        double crimesInYear1 =0;
        double crimesInYear2 =0;
        double totalCrimesYear1 =0;
         double totalCrimesYear2 =0;
        double crimeDifferencePercent =0;

         boolean year1Found = false;
         boolean year2Found = false;
           for (int i = 0; i<database.size(); i++){
            SFYear yearRecord=database.get(i);
            if (yearRecord.getcurrentYear() == year1){
                year1Found = true;
            }
            if (yearRecord.getcurrentYear() == year2){
                year2Found = true;
            }
           }
           if (!year1Found){
            readFile(year1 + ".csv");
           }
            if (!year2Found){
            readFile(year2 + ".csv");
           }
        for (int i=0; i<database.size();i++){
            SFYear currentYear = database.get(i);
                for (int j =0; j<currentYear.getRecordsForYear().size(); j++){
                    if (currentYear.getcurrentYear()== year1){
                        if (currentYear.getRecordsForYear().get(j).getDescription().indexOf(crimeDescription) != -1){
                            crimesInYear1++;
                        }
                        totalCrimesYear1 = currentYear.getRecordsForYear().size();
                    }
                    if (currentYear.getcurrentYear()== year2){
                        if (currentYear.getRecordsForYear().get(j).getDescription().indexOf(crimeDescription) != -1){
                            crimesInYear2++;
                        }
                        totalCrimesYear2 = currentYear.getRecordsForYear().size();
                    } 
                } 
                double crimesYear1Percent = (crimesInYear1/totalCrimesYear1)*100;
                double crimesYear2Percent = (crimesInYear2/totalCrimesYear2)*100;
                crimeDifferencePercent = crimesYear2Percent - crimesYear1Percent;
            
        }
        if (year1>year2){
            crimeDifferencePercent = 0 - crimeDifferencePercent;
        }
	    return crimeDifferencePercent; 
    }

    /**
     * This method outputs the NYC borough where the most amount of stops 
     * occurred in a given year. This method will mainly analyze the five 
     * following boroughs in New York City: Brooklyn, Manhattan, Bronx, 
     * Queens, and Staten Island.
     * 
     * @param year we are only interested in the records of year.
     * @return the borough with the greatest number of stops
     */
    public String mostCommonBorough ( int year ) {

        int brooklynCount =0;
        int manhattanCount = 0;
        int bronxCount = 0;
        int queensCount = 0;
        int statenCount = 0;
        String mostCommon = "";

         for (int i=0; i<database.size();i++){
            SFYear currentYear = database.get(i);
            if (currentYear.getcurrentYear()== year){
                for (int j =0; j<currentYear.getRecordsForYear().size(); j++){
                    if (currentYear.getRecordsForYear().get(j).getLocation().equals("BROOKLYN")){
                        brooklynCount++;
                    } else if (currentYear.getRecordsForYear().get(j).getLocation().equals("MANHATTAN")){
                        manhattanCount++;
                    } else if (currentYear.getRecordsForYear().get(j).getLocation().equals("BRONX")){
                        bronxCount++;
                    } else if (currentYear.getRecordsForYear().get(j).getLocation().equals("QUEENS")){
                            queensCount++;
                    } else if (currentYear.getRecordsForYear().get(j).getLocation().equals("STATEN ISLAND")){
                            statenCount++;
                    }
                } 

                int max = brooklynCount;
                mostCommon = "Brooklyn";
                if (manhattanCount>max){
                    max=manhattanCount;
                     mostCommon = "Manhattan";
                }
                if (bronxCount>max){
                    max = bronxCount;
                     mostCommon = "Bronx";
                }
                if (queensCount>max){
                    max = queensCount;
                     mostCommon = "Queens";
                }
                if (statenCount>max){
                    max = statenCount;
                     mostCommon = "Staten Island";
                }
            }
        }

        return mostCommon; // update the return value
    }

}
