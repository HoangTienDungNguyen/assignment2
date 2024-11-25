package cst8218.Hoang.slider.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Represents a slider entity that moves back and forth on a grid.
 * The slider's movement is defined by its position (x, y), size, 
 * and maximum travel distance. The slider moves left and right 
 * from its initial position based on the currentTravel attribute.
 * As the slider changes direction a certain number of times, its
 * maxTravel distance decreases until it stops.
 * 
 * @author hoang
 */
@Entity
@Table(name = "SLIDERS")
public class Slider implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Integer size = INITIAL_SIZE;

    @NotNull
    @Min(0)
    @Max(X_LIMIT)
    private Integer x;

    @NotNull
    @Min(0)
    @Max(Y_LIMIT)
    private Integer y;

    @NotNull
    private Integer currentTravel = INITIAL_SIZE;

    @NotNull
    @Min(1)
    @Max(MAX_TRAVEL_LIMIT)
    private Integer maxTravel;

    private Integer mvtDirection = 1;
    private Integer dirChangeCount = 0;

    // Constants
    public static final int INITIAL_SIZE = 10;
    public static final int TRAVEL_SPEED = 2;
    public static final int X_LIMIT = 800;
    public static final int Y_LIMIT = 600;
    public static final int MAX_TRAVEL_LIMIT = 100;
    public static final int MAX_DIR_CHANGES = 5;
    public static final int DECREASE_RATE = 1;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getCurrentTravel() {
        return currentTravel;
    }

    public void setCurrentTravel(Integer currentTravel) {
        this.currentTravel = currentTravel;
    }

    public Integer getMaxTravel() {
        return maxTravel;
    }

    public void setMaxTravel(Integer maxTravel) {
        this.maxTravel = maxTravel;
    }

    public Integer getMvtDirection() {
        return mvtDirection;
    }

    public void setMvtDirection(Integer mvtDirection) {
        this.mvtDirection = mvtDirection;
    }

    public Integer getDirChangeCount() {
        return dirChangeCount;
    }

    public void setDirChangeCount(Integer dirChangeCount) {
        this.dirChangeCount = dirChangeCount;
    }

    /**
     * Updates the properties to simulate the passing of one unit of time.
     * The slider moves in its current direction by a set speed. When the 
     * slider reaches its maxTravel distance, it reverses direction. 
     * After a certain number of direction changes, the maxTravel distance 
     * decreases, slowing the slider over time.
     */
    public void timeStep() {
        if (maxTravel > 0) {
            // Update currentTravel based on direction and speed
            currentTravel += mvtDirection * TRAVEL_SPEED;

            // If the currentTravel reaches or exceeds maxTravel, reverse direction
            if (Math.abs(currentTravel) >= maxTravel) {
                mvtDirection = -mvtDirection;
                dirChangeCount++;

                // If direction changes exceed the maximum, reduce maxTravel
                if (dirChangeCount > MAX_DIR_CHANGES) {
                    maxTravel -= DECREASE_RATE;
                    dirChangeCount = 0;
                }
            }
        }
    }

    /**
     * Updates non-null attributes from the current Slider to the existingSlider.
     * Only fields that are not null in this instance will overwrite the values in the existing instance.
     * 
     * @param existingSlider The existing Slider entity to update.
     */
    public void updateNonNullAttributes(Slider existingSlider) {
        if (this.getSize() != null) {
            existingSlider.setSize(this.getSize());
        }
        if (this.getX() != null) {
            existingSlider.setX(this.getX());
        }
        if (this.getY() != null) {
            existingSlider.setY(this.getY());
        }
        if (this.getCurrentTravel() != null) {
            existingSlider.setCurrentTravel(this.getCurrentTravel());
        }
        if (this.getMaxTravel() != null) {
            existingSlider.setMaxTravel(this.getMaxTravel());
        }
        if (this.getMvtDirection() != null) {
            existingSlider.setMvtDirection(this.getMvtDirection());
        }
        if (this.getDirChangeCount() != null) {
            existingSlider.setDirChangeCount(this.getDirChangeCount());
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Slider)) {
            return false;
        }
        Slider other = (Slider) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "cst8218.Hoang.slider.entity.Slider[ id=" + id + " ]";
    }
}
