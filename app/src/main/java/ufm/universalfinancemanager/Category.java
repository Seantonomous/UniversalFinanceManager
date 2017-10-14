/* Author: Sean Hansen
* ID: 108841276
* Date Started: 10/14/17
* Date Complete:
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members:
*/
package ufm.universalfinancemanager;

public class Category {
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}

    @Override
    public String toString() {
        return this.name;
    }
}
