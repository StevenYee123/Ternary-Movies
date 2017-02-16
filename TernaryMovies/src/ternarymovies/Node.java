/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ternarymovies;

/**
 *
 * @author steven
 */
public class Node {

    int value;
    int subtree;
    Node leftChild;
    Node midChild;
    Node rightChild;
    boolean end;
    public Node(int init) {
        value = init;
        //The tree starts off with an initial value of 0
        subtree = 0;
        end = true;
    }
}
