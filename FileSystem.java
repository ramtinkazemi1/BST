import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class FileSystem {
    BST<String, FileData> nameTree;
    BST<String, ArrayList<FileData>> dateTree;
    DateComparator dc;

    public FileSystem() {
        nameTree = new BST<>();
        dateTree = new BST<>();
        dc = new DateComparator();
    }

    public FileSystem(String input) {
        this();
        try {
            File file = new File(input);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(", ");
                add(data[0], data[1], data[2]);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    public void add(String name, String dir, String date) {
        if (!(name == null || dir == null || date == null)) {
            FileData fileData = new FileData(name, dir, date);
            nameTree.set(name, fileData);
            if (dateTree.containsKey(date)) {
                dateTree.get(date).add(fileData);
            } else {
                ArrayList<FileData> list = new ArrayList<>();
                list.add(fileData);
                dateTree.put(date, list);
            }
        }
    }

    public ArrayList<String> findFileNamesByDate(String date) {
        ArrayList<FileData> list = dateTree.get(date);
        if (list != null) {
            ArrayList<String> result = new ArrayList<>();
            for (FileData data : list) {
                result.add(data.name);
            }
            return result;
        } else {
            return null;
        }
    }

    public FileSystem filter(String startDate, String endDate) {
        FileSystem fileSystem = new FileSystem();
        for (String element : this.dateTree.keys()) {
            if (dc.compare(element, startDate) >= 0 && dc.compare(element, endDate) <= 0) {
                List<FileData> list = dateTree.get(element);
                for (FileData data : list) {
                    fileSystem.add(data.name, data.dir, data.lastModifiedDate);
                }
            }
        }
        return fileSystem;
    }

    public FileSystem filter(String wildCard) {
        wildCard = wildCard.toLowerCase();
        FileSystem fileSystem = new FileSystem();
        for (String name : this.nameTree.keys()) {
            if (name.toLowerCase().contains(wildCard)) {
                FileData data = nameTree.get(name);
                fileSystem.add(data.name, data.dir, data.lastModifiedDate);
            }
        }
        return fileSystem;
    }

    public List<String> outputNameTree() {
        List<String> result = new ArrayList<>();
        for (String element : this.nameTree.keys()) {
            result.add(element + ": " + nameTree.get(element));
        }
        return result;
    }

    public List<String> outputDateTree() {
        List<String> result = new ArrayList<>();
        for (String element : this.dateTree.keys()) {
            result.add(element + ": " + dateTree.get(element));
        }
        return result;
    }

    class DateComparator implements Comparator<String> {
        public int compare(String date1, String date2) {
            String[] dateOne = date1.split("/");
            String[] dateTwo = date2.split("/");
            LocalDate firstDate = LocalDate.of(Integer.parseInt(dateOne[2]), Integer.parseInt(dateOne[0]),
                    Integer.parseInt(dateOne[1]));
            LocalDate secondDate = LocalDate.of(Integer.parseInt(dateTwo[2]), Integer.parseInt(dateTwo[0]),
                    Integer.parseInt(dateTwo[1]));
            if (firstDate.isBefore(secondDate)) {
                return -1;
            } else if (firstDate.isAfter(secondDate)) {
                return 1;
            }
            return 0;
        }
    }
}
