/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Social;

import static Social.Social.socialNetworkExample;


public class Main {

    public static void main(String[] args) {     
        Social socialNetwork = socialNetworkExample();
        
        JFrameLogin loginJFframe = new JFrameLogin(socialNetwork);
       
        loginJFframe.setVisible(true);

    }
}