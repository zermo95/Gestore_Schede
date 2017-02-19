package gestoreSchede.oggetti.elementi;

import java.util.Observable;

/*******************
 * © APT Software *
 *******************/
public class Elemento extends Observable{
    private int id_elemento;

    public void setId_elemento(int id_elemento){
        this.id_elemento=id_elemento;
    }

    public int getId_elemento(){
        return  id_elemento;
    }
}
