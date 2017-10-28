
package cz.fi.muni.pa165;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 * The entity representing an Airplane.
 * 
 * @author Jan Cakl
 */

@Entity
public class Airplane implements Serializable {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(nullable = false)
    private String name;
    
    @NotNull
    @Column(nullable = false)
    private String type;
    
    @NotNull
    @Column(nullable = false)
    private int capacity;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    @Override
    public String toString() {
        return "Airplane{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                 ", capacity='" + capacity + '\'' +
                '}';
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
	if (!(obj instanceof Airplane)) return false;
        
        Airplane other = (Airplane) obj;
        
        if (capacity != other.getCapacity())
            return false;
        
	if (!name.equals(other.getName()))
            return false;
        
        return type.equals(other.getType());      
    }
    
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result += prime * result + Objects.hashCode(this.id);
        result += prime * result + this.capacity;
        result += prime * result + Objects.hashCode(this.name);
        result += prime * result + Objects.hashCode(this.type);
        return result;
    }
}
