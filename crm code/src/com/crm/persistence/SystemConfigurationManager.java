package com.crm.persistence;

public class SystemConfigurationManager {
    private static volatile SystemConfigurationManager instance;

    private int slaHours = 24;
    private int lowStockThreshold = 5;

    private SystemConfigurationManager() {}

    public static synchronized SystemConfigurationManager getInstance() {
        if (instance == null) {
            synchronized(SystemConfigurationManager.class)
            {
                if(instance==null)
                {
                    instance = new SystemConfigurationManager();
                }
            }
        }
        return instance;
    }


    public int getSlaHours() { return slaHours; }
    public void setSlaHours(int slaHours) { this.slaHours = slaHours; }

    public int getLowStockThreshold() { return lowStockThreshold; }
    public void setLowStockThreshold(int lowStockThreshold) { this.lowStockThreshold = lowStockThreshold; }
}
