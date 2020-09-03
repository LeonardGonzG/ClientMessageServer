/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

/**
 *
 * @author Leonardo González G.
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String mystring = "4000 leonardo hola como estas";
        String arr[] = mystring.split(" ", 2);

        String cod = arr[0];   //the
        String theRest = arr[1];     //quick brown fox
        
        String[] message = theRest.split(" ",2);
        
        
        System.out.println("cod: "+ cod);
        System.out.println(message[0].toUpperCase()+": ");
        System.out.println(message[1]);

    }

}
