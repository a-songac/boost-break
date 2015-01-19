package project.boostbreak;

import java.io.Serializable;

/** Class that defines the different exercises: their name, their duration and their description
 * 
 * @author Arnaud
 *
 */
public class Exercise implements Serializable {


	
	private String name;
	private int duration;//in seconds
	private String description;
	private boolean enabled;
	
	public enum Status {ENABLED, DISABLED};
	private static int nbExercises = 0;
	private static final long serialVersionUID = 8818910776675641469L;
	
	//constructors
	/**Default constructors that initializes an exercise object, with no precise name nor description,
	 * with a duration of 0 seconds and disabled state.
	 * 
	 */
	public Exercise(){
		name = "No Name";
		duration = 0;
		description = "no description";
		enabled = false;
		nbExercises++;
	}
	
	/** Three argument constructor that sets a name, a duration and a description and it is automatically enabled.
	 * 
	 * @param name Name of the exercise
	 * @param duration Duration in seconds of the exercise
	 * @param description Short description of the exercise
	 *
	 */
	public Exercise(String name, int duration, String description){
		this.name = name;
		this.duration = duration;
		this.description = description;
		this.enabled = true;
		nbExercises++;
	}
	
	//MUTATORS
	
	/**Mutator to set the name of the exercise.
	 * 
	 * @param name Name of the exercise
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**Mutator to set the duration of the exercise.
	 * 
	 * @param duration Duration of the exercise in seconds
	 */
	public void setDuration(int duration){
		//validation will be made in the android activity
		this.duration = duration;
		
	}
	
	/** Mutator to set the description of the exercise.
	 * 
	 * @param description Description of the exercise.
	 */
	public void setDescription(String description){
		this.description = description;
	}
	
	/**Mutator to set the status of the activity (enabled or disabled).
	 * 
	 * @param enabled Boolean value for status of the exercise
	 */
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}
	
	//ACCESSORS
	
	/**Accessor returning the name of the exercise.
	 * 
	 * @return Name of the exercise
	 */
	public String getName(){
		return name;
	}
	
	/**Accessor returning the duration of the exercise
	 * 
	 * @return Duration in seconds
	 */
	public int getDuration(){
		return duration;
	}
	
	/**Accessor returning the description of the exercise
	 * 
	 * @return Description of the exercise
	 */
	public String getDescription(){
		return description;
	}
	
	/**Accessor returing the status of the exercise
	 * 
	 * @return Status: enabled (true) or disabled (false)
	 */
	public boolean getEnabled(){
		return enabled;
	}


	
	//OTHER METHODS **********************************
	
	public String toString(){
		return "Exercise Name: " + name
				+ "\nExercise Duration: " + duration
				+ "\nExercise Description: " + description + "\n";
	}
	
	/**This method displays the duration of an exercise in the "00:00" format.
	 * 
	 * @return The format for the duration of the exercise
	 */
	public String displayDuration(){
		
		return duration/60 + " : " + String.format("%02d", (duration % 60));
	}
	
	
	

}
