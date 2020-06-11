package model;
import java.util.ArrayList;

/**
 * @author Kostadin Georgiev
 *
 */
public class Model {

    Thread[] threadArray;

    public Thread[] getAllThreads() {
        ThreadGroup rootGroup;
        ThreadGroup parentGroup;
        Thread[] threads;

        rootGroup = Thread.currentThread().getThreadGroup();
        // Navigate to the top of the thread group (to the system group).
        while ((parentGroup = rootGroup.getParent()) != null) {
            rootGroup = parentGroup;
        }
        threads = new Thread[rootGroup.activeCount()];
        // Placeholder to put our threads in.
        while (rootGroup.enumerate(threads, true) == threads.length) {
            // Must be at least len + 1 otherwise the table will not be generated due to the lack of space for each thread.
            threads = new Thread[threads.length + 1];
        }

        return threads;
    }


    public String[][] getThreadsInfo(Thread[] threads) {
        String[][] threadsInfo = new String[threads.length - 1][5];

        for(int i = 0; i < threads.length; i++) {
            if (threads[i] != null) {
                addThread(threads, threadsInfo, i);
            }
        }

        return threadsInfo;
    }

    public String[][] getThreadsInfoByName(Thread[] threads, String search) {
        String[][] threadsInfo = new String[threads.length - 1][5];
        for(int i = 0; i < threads.length; i++) {
            if (threads[i] != null) {
                if(threads[i].getName().toLowerCase().contains(search.toLowerCase())){
                    addThread(threads, threadsInfo, i);
                }
            }
        }
        return threadsInfo;
    }


    public Thread getThreadById(int id) {
        threadArray = getAllThreads();

        for (Thread thread : threadArray) {
            if (thread.getId() == id){
                return thread;
            }
        }

        return new Thread();
    }

    public void addThread(Thread[] threads, String[][] threadsInfo, int i){
        threadsInfo[i][0] = threads[i].getName();
        threadsInfo[i][1] = String.valueOf(threads[i].getId());
        threadsInfo[i][2] = threads[i].getState().toString();
        threadsInfo[i][3] = String.valueOf(threads[i].getPriority());
        if(threads[i].isDaemon()) {
            threadsInfo[i][4] = "true";
        } else {
            threadsInfo[i][4] = "false";
        }
    }

    public String[][] filterThreads(String[][] unfilteredData, String filterTerm){
        if(filterTerm.equalsIgnoreCase("none")){
            return unfilteredData;
        }

        ArrayList<Integer> rowsToKeep = new ArrayList<>();
        int i = 0;
        for (String[] row : unfilteredData) {
            if(row[0]!=null) {
                if (getThreadById(Integer.parseInt(row[1])).getThreadGroup().getName().equalsIgnoreCase(filterTerm)) {
                    rowsToKeep.add(i);
                }
            }
            i++;
        }
        String[][] newData = new String[rowsToKeep.size()][5];
        for (int c = 0; c < rowsToKeep.size(); c++){
            newData[c][0] = unfilteredData[rowsToKeep.get(c)][0];
            newData[c][1] = unfilteredData[rowsToKeep.get(c)][1];
            newData[c][2] = unfilteredData[rowsToKeep.get(c)][2];
            newData[c][3] = unfilteredData[rowsToKeep.get(c)][3];
            newData[c][4] = unfilteredData[rowsToKeep.get(c)][4];
        }
        return newData;
    }


}
