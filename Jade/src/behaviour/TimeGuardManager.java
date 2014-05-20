package behaviour;

import java.util.Date;

/**
 *
 * <h1>Time guar manager.</h1>
 * Allow management of a time guard, an interval of time to wait before doing something.
 */
public class TimeGuardManager
{
    private Date _startTime; /*!< Time guard start time.*/
    private Date _endTime; /*!< Time guard end time.*/
    
    /**
     * Constructor.
     */
    public TimeGuardManager()
    {
        _startTime = new Date();
        _endTime = new Date(_startTime.getTime());
    }
    
    /**
     * Initialize a new time guard.
     * @param duration time guard duration.
     */
    public void initialize(long duration)
    {
        _startTime = new Date();
        
        _endTime = new Date(_startTime.getTime()+duration*1000);
        
        System.out.println("TimeGuardManager::initialize : initilized to "+remainingTime());
    }
    
    /**
     * Tells whether the time guard is over or not.
     * @return true if the time guard is over.
     */
    public boolean isOver()
    {
        return _endTime.compareTo(new Date()) == -1;
    }
    
    /**
     * Get the remaining time of the time guard.
     * @return remaing time before the end of the time guard.
     */
    public long remainingTime()
    {
        long remaining = (_endTime.getTime() - (new Date()).getTime())/1000;
        
        return (remaining >= 0 ? remaining : 0);
    }
}
