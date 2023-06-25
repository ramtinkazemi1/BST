import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.Test;

public class FileSystemTest {

    public File makeFile(String[] data) {
        File file = new File("testFile.txt");
        if (file.exists()) {
            file.delete();
        }
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            for (String s : data) {
                writer.println(s);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    // tests
    @Test
    public void test1() {
        File file = makeFile(new String[] {
                "mySample.txt, /home, 02/01/2021",
                "mySample1.txt, /root, 02/01/2021",
                "mySample2.txt, /user, 02/06/2021"
        });
        FileSystem system = new FileSystem(file.getAbsolutePath());
        assertEquals("[mySample.txt: {Name: mySample.txt, Directory: /home, Modified Date: 02/01/2021}, "
                + "mySample1.txt: {Name: mySample1.txt, Directory: /root, Modified Date: 02/01/2021}, "
                + "mySample2.txt: {Name: mySample2.txt, Directory: /user, Modified Date: 02/06/2021}]",
                system.outputNameTree().toString());
    }

    @Test
    public void test2() {
        File file = makeFile(new String[] {
                "mySample.txt, /home, 02/01/2021",
                "mySample1.txt, /root, 02/01/2021",
                "mySample2.txt, /user, 02/06/2021"
        });
        FileSystem system = new FileSystem(file.getAbsolutePath());
        assertEquals("[mySample.txt, mySample1.txt]", system.findFileNamesByDate("02/01/2021").toString());
    }

    @Test
    public void test3() {
        File file = makeFile(new String[] {
                "mySample.txt, /home, 02/01/2021",
                "mySample1.txt, /root, 02/01/2021",
                "mySample2.txt, /user, 02/06/2021"
        });
        FileSystem system = new FileSystem(file.getAbsolutePath());
        assertEquals(null, system.findFileNamesByDate("02/08/2021"));
    }

    @Test
    public void test4() {
        File file = makeFile(new String[] {
                "mySample.txt, /home, 02/01/2021",
                "mySample1.txt, /root, 02/01/2021",
                "mySample2.txt, /user, 02/06/2021"
        });
        FileSystem system = new FileSystem(file.getAbsolutePath());
        assertEquals("[02/01/2021: [{Name: mySample1.txt, Directory: /root, Modified Date: 02/01/2021}]]", 
                system.filter("ple1").outputDateTree().toString());
    }

    @Test
    public void test5() {
        File file = makeFile(new String[] {
                "mySample.txt, /home, 02/01/2021",
                "mySample1.txt, /root, 02/01/2021",
                "mySample2.txt, /user, 02/06/2021"
        });
        FileSystem system = new FileSystem(file.getAbsolutePath());
        assertEquals("[02/01/2021: [{Name: mySample.txt, Directory: /home, Modified Date: 02/01/2021}, "
                + "{Name: mySample1.txt, Directory: /root, Modified Date: 02/01/2021}]]", 
                system.filter("02/01/2021", "02/01/2021").outputDateTree().toString());
    }

    @Test
    public void test6() {
        File file = makeFile(new String[] {
                "mySample.txt, /home, 02/01/2021",
                "mySample1.txt, /root, 02/01/2021",
                "mySample2.txt, /user, 02/06/2021"
        });
        FileSystem system = new FileSystem(file.getAbsolutePath());
        assertEquals("[]", system.filter("02/11/2021", "02/15/2021").outputDateTree().toString());
    }
}
