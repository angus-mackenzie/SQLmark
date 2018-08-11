public class TestCSVReader{
    CSVReader csvReader;
    BufferedReader reader;
    File file;

    @Before
    public void init(){
        try{
            csvReader = new CSVReader();
            file = new File("../src/matricData.csv");
            reader = new BufferedReader(new FileReader(file));
        }catch(IOException e){
            e.printStackTrace();
        }

    }


    @Test
    void testRead() throws Exception{

    }
}