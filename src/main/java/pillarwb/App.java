package pillarwb;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
public class App extends JFrame{
    private JComboBox<String> countryComboBox;
    private JTextField startDateField, endDateField, projectIDField, saveDirectoryField;
    private void initComponents(){
        setSize(917, 544);
        // Fonts
        // int fontSizeG = 26;
        // int fontSizeR = 18;
        // String fontFam = "Segoe UI";
        // Download button
        // downloadButton = new JButton();
        // // dropbox
        // country = new JComboBox<>();
        // docType = new JComboBox<>();
        // // inputtex
        // projID = new JTextField();
        // startDate = new JTextField();
        // endDate = new JTextField();
        // saveDir = new JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // lTitle.setFont(new java.awt.Font(fontFam, 0, fontSizeG));
        // lTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        // lTitle.setText("Scrapper");

        
        
        countryComboBox = new JComboBox<>(new String[]{"Aruba", "Option 2", "Option 3", "Option 4", "Option 5", "Option 6"});
        startDateField = new JTextField("Start");
        add(startDateField);
        System.out.println(startDateField);
        
        setVisible(true);
    }
    public App() {
        initComponents();
    }

    /**
     * The main function creates an instance of the AppBackend class and makes it visible.
     */
    public static void main(String[] args) {
        AppBackend desk = new AppBackend();
        desk.setVisible(true);
        

    }
    // private JButton downloadButton;
    // private JComboBox country, docType;
    // private JTextField projID, saveDir, startDate, endDate;
    // private JLabel lTitle, lCountry, lDocType, lProjID, lSaveDir, lStartDate, lendDate;
}
