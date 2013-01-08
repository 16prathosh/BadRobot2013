package badrobot.com.subsystems;

import badrobot.com.OI;
import badrobot.com.subsystems.interfaces.Logger;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

/**
 * @author Jon Buckley
 */
public abstract class BadSubsystem extends Subsystem implements Logger, Sendable, ITableListener
{   
    //has constructor been called?
    protected boolean CONSTRUCTED = false;
    
    //is logging enabled
    protected boolean CONSOLE_OUTPUT_ENABLED = true;
    
    /**
     * Subclasses should implement their own implementations on initialize. This
     * method is meant to instantiate any hardware or variables that will be 
     * needed. This is specific to each class and can be left blank.
     */
    protected abstract void initialize();
    
    //provides automatic NetworkTable compliance
    protected ITable table;
    
    /**
     * provides compliance with Sendable interface. This automatically adds the
     * subsystem to the SmartDashboard.
     */
    public ITable getTable()
    {
        System.out.println("getting table");
        return table;
    }
    
    /**
     * inherited by Sendable interface, overriding the default from Subsystem
     * @return the type of SmartDashboard data being sent
     */
    public String getSmartDashboardType()
    {
        return "Subsystem";
    }
    
    /**
     * initializes table, handing a table down to store values in
     * @param t the table that should be stored and have values added to
     */
    public void initTable(ITable t)
    {
        System.out.println("initing table");
        table = t;
        addNetworkTableValues(table);
    }
    
    /**
     * Event handler for when a value of a variable put in the network has 
     * changed. This should have some sort of logic to figure out which variable
     * it was the key parameter and change the local value of the variable to 
     * reflect it.
     * @param key the name of the variable in the NetworkTable
     * @param value the value that the variable has been changed to
     */
     public abstract void valueChanged(ITable itable, String key, Object value, boolean bln);
          
    /**
     * puts all pertinent values into the table (speeds, frequencies, any value 
     * that would be tracked or modified)
     * @param table the table that is being initialized and needs values
     */
    protected abstract void addNetworkTableValues(ITable table);
    
    /**
     * logs the string to be outputted. Enabled only if the master boolean is 
     * enabled. Calls the getConsoleIdentity method that grabs the identity 
     * that is wished to appear in the console. Most likely, this will just be
     * the class name.
     * @param out the string to be outputted
     */
    public void log(String out)
    {
        if (CONSOLE_OUTPUT_ENABLED && OI.CONSOLE_OUTPUT_ENABLED)
        {
            System.out.println(getConsoleIdentity() + ": " + out);
        }
    }
    
    /**
     * @return The String that should appear whenever this Subsystem outputs a String. 
     * Can be whatever you want, most likely the class name though.
     */
    public abstract String getConsoleIdentity();
}
