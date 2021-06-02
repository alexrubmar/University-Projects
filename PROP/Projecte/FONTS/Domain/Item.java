package Domain;

public class Item  {

    public String id;
    public String valor;

    public Item (String id, String valor) {
        this.id = id;
        this.valor = valor;
    }
    public boolean equals(Item a) {
        return (this.id.equals(a.id) && this.valor.equals(a.valor));
    }
}
