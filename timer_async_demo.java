/**
 * The purpose of this file is to give a rough idea about how to implement a timer
 * and call it in the Async Task. This code will not be "copy paste" but you should 
 * be able to use large chucks of it in your program. There are comments along the 
 * way to help understand the code better.
 * 
 * Author: Manav Ray <manavr>
 * Date: 03/02/2021
 */


/**
 * Timer class - you will most likely have this in its own file and should be able to use
 * most of the logic from here.
 *
 * Logic should be pretty easy to follow. You can post on piazza/discord or come to my office
 * hours if you have any other questions.
 */
public class Timer {

    private ArrayList<String> timeList;
    private int sec;
    private int min;
    private int hr;

    public CalcTime()
    {
        timeList = new ArrayList<String>();
        sec = 0;
        min = 0;
        hr = 0;
    }

    public String getTimeList() {
        return timeList;
    }

    public int getSeconds() {
        return sec;
    }

    public int getMinutes() {
        return min;
    }

    public int getHours() {
        return hr;
    }

    public void calc()
    {
        sec++;
        if (sec == 60)
        {
            sec = 0;
            min++;
        }
        if (min == 60)
        {
            min = 0;
            hr++;
        }
    }

    public String addTime(String time)
    {
        timeList.add(time);
        return timeList;
    }

    public void reset()
    {
        sec = 0;
        min = 0;
        hr = 0;
        timeList = = new ArrayList<String>();
    }    
}


/**
 * AsyncTask class. You will most likely have this nested within your controller class.
 * Your logic could also have this be a Fragment.
 */
public class ControlFragment extends Fragment </*Whatever you may need*/> {
    /**
     * In addition to other fields, you will need these:
     */
    private TimerAsyncTask asynctask; // will use this in controller to execute timer.
    boolean running; // boolean to check whether timer should be running or not (changed by clicking on start/stop button).
    Timer timer; // timer used in AsyncTask and Controller.
    TextView timerText; // the textview in your UI to display time.
  
        private class TimerAsyncTask extends AsyncTask<Integer, Integer, Void> {

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                String curr_time = String.format("%02d:%02d:%02d", values[0], values[1], values[2]); // used to properly format string.
                timerText.setText(curr_time); // update UI.
            }

            @Override
            protected void doInBackground(Integer... times) {
                while (running) { // running boolean must be updated in controller.
                    timer.calc(); // calculate time.
                    publishProgress(timer.getHours(), timer.getMinutes(), timer.getSeconds()); // publish progress fomr onProgressUpdate method to be triggered.
                    try {
                        Thread.sleep(1000); // sleep for 1 second (1000 milliseconds).
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                }
                return null;
            }
        }
}
