/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectakhir;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Refanda Dicky P
 */
public class ControlLogin {
    public ControlLogin(Login login, ModelLogin ML){
        login.getbtnLogin().addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e){
                String username = login.get_username().getText();
                String password = login.get_password().getText();
                
                if(ML.isLogin(username, password)){
                    JOptionPane.showMessageDialog(null, "berhasil login");
                    new Home().show();
                    login.dispose();
                }
                else {
                    JOptionPane.showMessageDialog(null, "gagal login");
                }
            }
        });
    }
}
