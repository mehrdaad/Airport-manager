package cz.fi.muni.pa165.dto;



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
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + capacity;
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
