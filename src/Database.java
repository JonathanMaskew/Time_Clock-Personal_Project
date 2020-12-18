import java.io.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Database {
    private static File file;
    private static BufferedReader bfr;
    private static BufferedWriter bfw;

    private static ArrayList<Person> people;

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

        bfr = new BufferedReader(new FileReader(file));
        bfw = new BufferedWriter(new FileWriter(file, true));

        ArrayList<String> hours = new ArrayList<String>();
        String line = bfr.readLine();
        while (line != null)    {
            hours.add(line);
            line = bfr.readLine();
        }

        return hours;
    }

    private static void updateReadersAndWriters() throws IOException {
        bfr = new BufferedReader(new FileReader(file));
        bfw = new BufferedWriter(new FileWriter(file, true));
    }

    public boolean isRunning() throws IOException {
        updateReadersAndWriters();

        String line = bfr.readLine();
        int totalLines = 0;

        while (line != null)    {
            totalLines++;
            line = bfr.readLine();
        }

        System.out.println(totalLines);

        if (totalLines % 2 == 0)    {
            return false;
        } else  {
            return true;
        }
    }

    public void startClock() throws IOException {
        bfw.write(LocalDateTime.now().getDayOfYear() + ", " + LocalDateTime.now().getMonth() + "/" + LocalDateTime.now().getDayOfMonth() + "/" + LocalDateTime.now().getYear() + ", " + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
        bfw.flush();
        bfw.newLine();
        bfw.flush();
    }

    public void stopClock() throws IOException {
        bfw.write(LocalDateTime.now().getDayOfYear() + ", " + LocalDateTime.now().getMonth() + "/" + LocalDateTime.now().getDayOfMonth() + "/" + LocalDateTime.now().getYear() + ", " + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + " |");
        bfw.flush();
        bfw.newLine();
        bfw.flush();
    }

    public void saveToFile() throws IOException {
        File accountFile = new File("accounts.bin");
        BufferedReader accountBfr = new BufferedReader(new FileReader(accountFile));
        BufferedWriter accountBfw = new BufferedWriter(new FileWriter(accountFile, true));

        for (int i = 0; i < people.size(); i++) {
            accountBfw.write(people.get(i).getName() + ", " + people.get(i).getUsername() + ", " + people.get(i).getPassword());
            accountBfw.flush();
            accountBfw.newLine();
            accountBfw.flush();
        }
    }

    public Person userExists(String username, String password) throws IOException {
        File accountFile = new File("accounts.bin");
        BufferedReader accountBfr = new BufferedReader(new FileReader(accountFile));
        BufferedWriter accountBfw = new BufferedWriter(new FileWriter(accountFile, true));

        System.out.println(username);
        System.out.println(password);

        String line = accountBfr.readLine();
        while (line != null)    {
            System.out.println(line.substring(line.indexOf(", ") + 2, line.lastIndexOf(", ")));
            System.out.println(line.substring(line.lastIndexOf(", ") + 2));

            if (line.substring(line.indexOf(", ") + 2, line.lastIndexOf(", ")).equals(username) && line.substring(line.lastIndexOf(", ") + 2).equals(password))    {
                return new Person(line.substring(0, line.indexOf(", ")), line.substring(line.indexOf(", ") + 2, line.lastIndexOf(", ")), line.substring(line.lastIndexOf(", ") + 2));
            }
            line = accountBfr.readLine();
        }
        return null;
    }

    public ArrayList<String> calculateTimes() throws IOException {
        updateReadersAndWriters();

        ArrayList<String> frameLines = new ArrayList<>();
        String lineOne = bfr.readLine();
        String lineTwo = bfr.readLine();
        while (lineOne != null)    {
            ChronoUnit.MINUTES.between();

            int lineOneDays = Integer.parseInt(lineOne.substring(0, lineOne.indexOf(", ")));
            int lineTwoDays = Integer.parseInt(lineTwo.substring(0, lineOne.indexOf(", ")));

            if (lineOneDays != lineTwoDays) {
                lineTwoDays
            }
        }
    }
}
