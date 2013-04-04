/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Client.client;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 *
 * @author Siniša
 */
public class GUIClient extends javax.swing.JFrame implements ActionListener  {
  
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JRadioButton jRadioButton1;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextArea jTextArea1;
    public javax.swing.JTextField jTextField1;
    
    
     public GUIClient() {
        initComponents();
    }
        
      private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
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
  
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);
        jTextArea1.setEditable(false);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 50, 380, 150);

        jButton1.setText("Allow connections");
        getContentPane().add(jButton1);
        jButton1.setBounds(210, 210, 150, 30);
        jButton1.addActionListener(this);
        
        jButton2.setText("Exit");       
        jButton2.setBounds(210, 210, 150, 30);
        jButton2.addActionListener(this);

        pack();
    }// </editor-fold>           
      
       public void actionPerformed(ActionEvent e) {     
           if(e.getSource() == jButton1){
                client.allow=true; 
                jButton1.setText("Close");
                getContentPane().remove(jButton1);
                getContentPane().add(jButton2);
           }
          
            if(e.getSource() == jButton2){                
                 System.exit(0);              
            }
           
           
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
