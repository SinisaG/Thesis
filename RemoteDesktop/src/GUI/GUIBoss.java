/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Boss.server;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 *
 * @author Siniša
 */
public class GUIBoss extends javax.swing.JFrame implements ActionListener  {
  
    public javax.swing.JButton jButton1;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JRadioButton jRadioButton1;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextArea jTextArea1;
    public javax.swing.JTextField jTextField1;
    public javax.swing.JTextField jTextField2;
    
    
     public GUIBoss() {
        initComponents();
    }
        
      private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Remote desktop");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(70, 0, 250, 60);

        jLabel2.setText("Your ID:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(10, 210, 50, 20);

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
   
        getContentPane().add(jTextField1);
        jTextField1.setBounds(80, 210, 100, 30);

        jLabel3.setText("Connect to:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(10, 240, 80, 20);

        jTextField2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        getContentPane().add(jTextField2);
        jTextField2.setBounds(80, 240, 100, 30);    

        jRadioButton1.setText("Do not use the server");
        getContentPane().add(jRadioButton1);
        jRadioButton1.setBounds(210, 210, 180, 23);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);
        jTextArea1.setEditable(false);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 50, 380, 150);

        jButton1.setText("Connect");
        getContentPane().add(jButton1);
        jButton1.setBounds(210, 240, 150, 30);
        jButton1.addActionListener(this);

        pack();
    }// </editor-fold>           
      
       public void actionPerformed(ActionEvent e) {       
           server.wantToConnect=true;
           server.connectingID= Integer.parseInt(jTextField2.getText()) ;         
        }

        public void windowClosing(WindowEvent e) {
        
        }

        public void windowOpened(WindowEvent e) {}
        public void windowActivated(WindowEvent e) {}
        public void windowIconified(WindowEvent e) {}
        public void windowDeiconified(WindowEvent e) {}
        public void windowDeactivated(WindowEvent e) {}
        public void windowClosed(WindowEvent e) {}
      
    
}
