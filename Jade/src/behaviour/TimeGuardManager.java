/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package behaviour;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author josuah
 */
public class TimeGuardManager
{
    private Date _startTime;
    private Date _endTime;
    
    public TimeGuardManager()
    {
        _startTime = new Date();
        _endTime = new Date(_startTime.getTime());
    }
    
    public void initialize(long duration)
    {
        _startTime = new Date();
        
        _endTime = new Date(_startTime.getTime()+duration*1000);
        
        System.out.println("TimeGuardManager::initialize : initilized to "+remainingTime());
    }
    
    public boolean isOver()
    {
        return _startTime.compareTo(_endTime) == -1;
    }
    
    public long remainingTime()
    {
        long remaining = (_endTime.getTime() - (new Date()).getTime())/1000;
        
        return (remaining >= 0 ? remaining : 0);
    }
}
