package cz.fi.muni.pa165.dto;

import java.util.Objects;

/**
 *
 * @author Jan Cakl
 */
public class AirplaneDTO {
    
    private Long id;
    
    private String name;
    
    private String type;
    
    private int capacity;
    
    public Long getId() {
        return id;
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

    public void setCapacity(int capacity) throws IllegalArgumentException{
        this.capacity = capacity;
    }
    
    @Override
   public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
	if (!(obj instanceof AirplaneDTO)) return false;
        
        AirplaneDTO other = (AirplaneDTO) obj;
        
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
        result = prime * result + Objects.hashCode(this.name);
        result = prime * result + this.capacity;
        result = prime * result + Objects.hashCode(this.type);
        return result;
    }
    
    @Override
    public String toString() {
        return "AirplaneDTO{" +
                "name='" + name + '\'' +
                ", capacity='" + capacity + '\'' +
                ", type='" + type + '\'' +
                '}';
} 
    
}
