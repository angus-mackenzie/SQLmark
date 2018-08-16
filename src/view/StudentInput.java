package view;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 * Creates a basic UI for the user to enter in SQL commands
 * @author Angus Mackenzie
 * @version 15/08/2018
 */
public class StudentInput extends javax.swing.JFrame {

    /**
     * Creates new form for users to submit their queries
     */
    Connection dbConnection;
    public StudentInput(Connection dbConnection) {
        initComponents();
        this.setVisible(true);
        this.dbConnection = dbConnection;

    }
    //Don't edit this
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        textField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        scrollPanel = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        welcomeLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SQL Automarker");

        textField.setToolTipText("Please enter a query");
        sendButton.setText("Submit");
        sendButton.setToolTipText("Click to submit your query");

        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        textArea.setEditable(false);
        textArea.setColumns(20);
        textArea.setRows(5);
        textArea.setToolTipText("Output");
        scrollPanel.setViewportView(textArea);

        welcomeLabel.setText("An SQL Automarker");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(scrollPanel)
                        .addComponent(welcomeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(welcomeLabel)
                                .addGap(6, 6, 6)
                                .addComponent(scrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }
    public void appendTextArea(ResultSet rs) throws Exception{
            ResultSetMetaData rsMetaData = rs.getMetaData();
            StringBuilder output = new StringBuilder();
            int numberOfColumns = rsMetaData.getColumnCount();
            for (int i = 1; i < numberOfColumns + 1; i++) {
                String columnName = rsMetaData.getColumnName(i);
                output.append(columnName + "   ");

            }
            output.append("\n");
            output.append("----------------------------------------------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                for (int i = 1; i < numberOfColumns + 1; i++) {
                    output.append(rs.getString(i) + "   ");
                }
                output.append("\n");
            }
        textArea.append(output.toString());
    }
    public String getTextField(){
        return textField.getText();
    }
    public void setTextFieldBlank(){
        textField.setText("");
    }
    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String input = getTextField();
        if(input.equals("")){
            System.out.println("Input blank, ignored");
        }else{
            try{
                Statement query = dbConnection.createStatement();
                ResultSet results = query.executeQuery(input);
                appendTextArea(results);
            }catch(Exception e){
                e.printStackTrace();
            }

        }
        //dbConnection
    }
    public void setTextAreaBlank(){
        textArea.setText("");
    }
    public void setLabel(String input){
        welcomeLabel.setText(input);
    }
    // Variables declaration - do not modify
    private javax.swing.JScrollPane scrollPanel;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextArea textArea;
    private javax.swing.JTextField textField;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration
}