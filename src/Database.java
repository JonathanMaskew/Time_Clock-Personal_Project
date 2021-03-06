import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

public class Database {
    private static File file;
    private static BufferedReader bfr;
    private static BufferedWriter bfw;

    private static ArrayList<Person> people;

    private static File accountFile;
    private static BufferedReader accountBfr;
    private static BufferedWriter accountBfw;

    public Database()   {
        people = new ArrayList<>();
    }

    public void addPerson(Person p) throws IOException {
        people.add(p);
        saveToFile();
    }

    public ArrayList<String> getInformation(String filename) throws IOException {
        file = new File(filename);
        file.createNewFile();

        updateReadersAndWriters();

        return calculateTimes();
    }

    private static void updateReadersAndWriters() throws IOException {
        bfr = new BufferedReader(new FileReader(file));
        bfw = new BufferedWriter(new FileWriter(file, true));
    }

    public String isRunning() throws IOException {
        updateReadersAndWriters();

        String line = bfr.readLine();
        int totalLines = 0;
        String runningSince = "";
        String runningDate = "";

        while (line != null)    {
            totalLines++;

            runningDate = line.substring(line.indexOf(",") + 2, line.indexOf("/")).charAt(0) + line.substring(line.indexOf(",") + 3, line.indexOf(",") + 5).toLowerCase() + " " + line.substring(line.indexOf("/") + 1, line.lastIndexOf("/")) + ", " + line.substring(line.lastIndexOf("/") + 1, line.lastIndexOf(",")) + " at ";
            runningSince = line.substring(line.lastIndexOf(", ") + 2);
            if (Integer.parseInt(runningSince.substring(0, runningSince.indexOf(":"))) > 12)   {
                runningSince = runningDate + Integer.toString(Integer.parseInt(runningSince.substring(0, runningSince.indexOf(":"))) - 12) + ":" + runningSince.substring(runningSince.indexOf(":") + 1) + "pm";
            } else if (Integer.parseInt(runningSince.substring(0, runningSince.indexOf(":"))) == 12)    {
                runningSince = runningDate + Integer.toString(Integer.parseInt(runningSince.substring(0, runningSince.indexOf(":"))) - 12) + ":" + runningSince.substring(runningSince.indexOf(":") + 1) + "pm";
            } else  {
                runningSince = runningDate + Integer.toString(Integer.parseInt(runningSince.substring(0, runningSince.indexOf(":"))) - 12) + ":" + runningSince.substring(runningSince.indexOf(":") + 1) + "am";
            }

            if (runningSince.substring(runningSince.indexOf(":") + 1, runningSince.indexOf("m") - 1).length() == 1)    {
                runningSince = runningSince.substring(0, runningSince.indexOf(":")) + ":0" + runningSince.substring(runningSince.indexOf(":") + 1);
            }

            line = bfr.readLine();
        }

        if (totalLines % 2 == 0)    {
            return null;
        } else  {
            return runningSince;
        }
    }

    public void startClock() throws IOException {
        bfw.write(LocalDateTime.now().getDayOfYear() + ", " + LocalDateTime.now().getMonth() + "/" + LocalDateTime.now().getDayOfMonth() + "/" + LocalDateTime.now().getYear() + ", " + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
        bfw.flush();
        bfw.newLine();
        bfw.flush();
    }

    public void stopClock() throws IOException {
        bfw.write(LocalDateTime.now().getDayOfYear() + ", " + LocalDateTime.now().getMonth() + "/" + LocalDateTime.now().getDayOfMonth() + "/" + LocalDateTime.now().getYear() + ", " + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
        bfw.flush();
        bfw.newLine();
        bfw.flush();
    }

    public void saveToFile() throws IOException {
        accountFile = new File("accounts.bin");
        accountFile.createNewFile();

        accountBfr = new BufferedReader(new FileReader(accountFile));
        accountBfw = new BufferedWriter(new FileWriter(accountFile, true));

        for (int i = 0; i < people.size(); i++) {
            accountBfw.write(people.get(i).getName() + ", " + people.get(i).getUsername() + ", " + people.get(i).getPassword());
            accountBfw.flush();
            accountBfw.newLine();
            accountBfw.flush();
        }
    }

    public Person userExists(String username, String password) throws IOException {
        accountFile = new File("accounts.bin");
        accountFile.createNewFile();

        accountBfr = new BufferedReader(new FileReader(accountFile));
        accountBfw = new BufferedWriter(new FileWriter(accountFile, true));

        String line = accountBfr.readLine();
        while (line != null)    {
            if (line.substring(line.indexOf(", ") + 2, line.lastIndexOf(", ")).equals(username) && line.substring(line.lastIndexOf(", ") + 2).equals(password))    {
                return new Person(line.substring(0, line.indexOf(", ")), line.substring(line.indexOf(", ") + 2, line.lastIndexOf(", ")), line.substring(line.lastIndexOf(", ") + 2));
            }
            line = accountBfr.readLine();
        }
        return null;
    }

    public boolean usernameTaken(String username) throws IOException {
        accountFile = new File("accounts.bin");
        accountFile.createNewFile();

        accountBfr = new BufferedReader(new FileReader(accountFile));
        accountBfw = new BufferedWriter(new FileWriter(accountFile, true));

        String line = accountBfr.readLine();
        if (line != null) {
            if (line.substring(line.indexOf(", ") + 2, line.lastIndexOf(", ")).equals(username)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> calculateTimes() throws IOException {
        updateReadersAndWriters();

        ArrayList<String> frameLines = new ArrayList<>();

        String lineOne = bfr.readLine();
        String lineTwo = bfr.readLine();

        while (lineTwo != null)    {
            String completeLine = "";
            int totalMinutes = 0;
            boolean timeOnePm = false;
            boolean timeTwoPm = false;

            int dayOne = Integer.parseInt(lineOne.substring(0, lineOne.indexOf(", ")));
            int dayTwo = Integer.parseInt(lineTwo.substring(0, lineTwo.indexOf(", ")));

            int timeOneInMin = Integer.parseInt(lineOne.substring(lineOne.lastIndexOf(", ") + 2, lineOne.indexOf(":"))) * 60 + Integer.parseInt(lineOne.substring(lineOne.indexOf(":") + 1));
            int timeTwoInMin = Integer.parseInt(lineTwo.substring(lineTwo.lastIndexOf(", ") + 2, lineTwo.indexOf(":"))) * 60 + Integer.parseInt(lineTwo.substring(lineTwo.indexOf(":") + 1));

            String monthOne = lineOne.substring(lineOne.indexOf(", ") + 2, lineOne.indexOf("/"));
            String dateOne = lineOne.substring(lineOne.indexOf("/") + 1, lineOne.lastIndexOf("/"));
            String yearOne = lineOne.substring(lineOne.lastIndexOf("/") + 1, lineOne.lastIndexOf(", "));

            String monthTwo = lineTwo.substring(lineTwo.indexOf(", ") + 2, lineTwo.indexOf("/"));
            String dateTwo = lineTwo.substring(lineTwo.indexOf("/") + 1, lineTwo.lastIndexOf("/"));
            String yearTwo = lineTwo.substring(lineTwo.lastIndexOf("/") + 1, lineTwo.lastIndexOf(", "));

            int daysBetween = -1;
            if (dayOne != dayTwo)   {
                daysBetween = dayTwo - dayOne;
            }

            if (daysBetween != -1)  {
                if (timeTwoInMin > timeOneInMin)  {
                    totalMinutes += daysBetween * 1440;
                    totalMinutes += timeTwoInMin - timeOneInMin;
                } else  {
                    totalMinutes += (daysBetween - 1) * 1440;
                    totalMinutes += 1440 - timeOneInMin;
                    totalMinutes += timeTwoInMin;
                }
            } else  {
                totalMinutes += timeTwoInMin - timeOneInMin;
            }

            int finalTimeOneHours = timeOneInMin / 60;
            if (finalTimeOneHours > 12) {
                finalTimeOneHours = finalTimeOneHours - 12;
                timeOnePm = true;
            } else if (finalTimeOneHours == 12) {
                timeOnePm = true;
            }

            String finalTimeOneMins = Integer.toString(timeOneInMin % 60);
            if (finalTimeOneMins.length() == 1)   {
                finalTimeOneMins = "0" + finalTimeOneMins;
            }

            int finalTimeTwoHours = timeTwoInMin / 60;
            if (finalTimeTwoHours > 12) {
                finalTimeTwoHours = finalTimeTwoHours - 12;
                timeTwoPm = true;
            } else if (finalTimeTwoHours == 12) {
                timeTwoPm = true;
            }

            String finalTimeTwoMins = Integer.toString(timeTwoInMin % 60);
            if (finalTimeTwoMins.length() == 1)   {
                finalTimeTwoMins = "0" + finalTimeTwoMins;
            }

            int finalHours = totalMinutes / 60;
            int finalMinutes = totalMinutes % 60;

            if (dateOne.equals(dateTwo))    {
                if (timeOnePm) {
                    if (timeTwoPm)  {
                        completeLine = monthOne.charAt(0) + monthOne.substring(1, 3).toLowerCase() + " " + dateOne + ", " + yearOne + " from " + finalTimeOneHours + ":" + finalTimeOneMins + "pm to " + finalTimeTwoHours + ":" + finalTimeTwoMins + "pm";
                    } else  {
                        completeLine = monthOne.charAt(0) + monthOne.substring(1, 3).toLowerCase() + " " + dateOne + ", " + yearOne + " from " + finalTimeOneHours + ":" + finalTimeOneMins + "pm to " + finalTimeTwoHours + ":" + finalTimeTwoMins + "am";
                    }
                } else  {
                    if (timeTwoPm) {
                        completeLine = monthOne.charAt(0) + monthOne.substring(1, 3).toLowerCase() + " " + dateOne + ", " + yearOne + " from " + finalTimeOneHours + ":" + finalTimeOneMins + "am to " + finalTimeTwoHours + ":" + finalTimeTwoMins + "pm";
                    } else  {
                        completeLine = monthOne.charAt(0) + monthOne.substring(1, 3).toLowerCase() + " " + dateOne + ", " + yearOne + " from " + finalTimeOneHours + ":" + finalTimeOneMins + "am to " + finalTimeTwoHours + ":" + finalTimeTwoMins + "am";
                    }
                }
            } else  {
                if (timeOnePm) {
                    if (timeTwoPm) {
                        completeLine = monthOne.charAt(0) + monthOne.substring(1, 3).toLowerCase() + " " + dateOne + ", " + yearOne + " at " + finalTimeOneHours + ":" + finalTimeOneMins + "pm to " + monthTwo.charAt(0) + monthTwo.substring(1, 3).toLowerCase() + " " + dateTwo + ", " + yearTwo + " at " + finalTimeTwoHours + ":" + finalTimeTwoMins + "pm";
                    } else  {
                        completeLine = monthOne.charAt(0) + monthOne.substring(1, 3).toLowerCase() + " " + dateOne + ", " + yearOne + " at " + finalTimeOneHours + ":" + finalTimeOneMins + "pm to " + monthTwo.charAt(0) + monthTwo.substring(1, 3).toLowerCase() + " " + dateTwo + ", " + yearTwo + " at " + finalTimeTwoHours + ":" + finalTimeTwoMins + "am";
                    }
                } else  {
                    if (timeTwoPm)  {
                        completeLine = monthOne.charAt(0) + monthOne.substring(1, 3).toLowerCase() + " " + dateOne + ", " + yearOne + " at " + finalTimeOneHours + ":" + finalTimeOneMins + "am to " + monthTwo.charAt(0) + monthTwo.substring(1, 3).toLowerCase() + " " + dateTwo + ", " + yearTwo + " at " + finalTimeTwoHours + ":" + finalTimeTwoMins + "pm";
                    } else  {
                        completeLine = monthOne.charAt(0) + monthOne.substring(1, 3).toLowerCase() + " " + dateOne + ", " + yearOne + " at " + finalTimeOneHours + ":" + finalTimeOneMins + "am to " + monthTwo.charAt(0) + monthTwo.substring(1, 3).toLowerCase() + " " + dateTwo + ", " + yearTwo + " at " + finalTimeTwoHours + ":" + finalTimeTwoMins + "am";
                    }
                }
            }

            if (finalHours == 0 && finalMinutes != 0) {
                if (finalMinutes != 1) {
                    completeLine = completeLine + " totaling " + finalMinutes + " minutes.";
                } else  {
                    completeLine = completeLine + " totaling " + finalMinutes + " minute.";
                }
            } else if (finalHours != 0 && finalMinutes == 0)   {
                completeLine = completeLine + " totaling " + finalHours + " hours.";
            } else if (finalHours != 0 && finalMinutes != 0)  {
                if (finalMinutes != 1) {
                    completeLine = completeLine + " totaling " + finalHours + " hours and " + finalMinutes + " minutes.";
                } else  {
                    completeLine = completeLine + " totaling " + finalHours + " hours and " + finalMinutes + " minute.";
                }
            }

            if (completeLine.contains("totaling")) {
                frameLines.add(completeLine);
            }

            lineOne = bfr.readLine();
            lineTwo = bfr.readLine();
        }
        return frameLines;
    }

    public void clear(String filename) throws IOException {
        file.delete();

        file = new File(filename);
        file.createNewFile();

        updateReadersAndWriters();
    }
}
