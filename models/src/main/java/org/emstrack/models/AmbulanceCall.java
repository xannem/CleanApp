package org.emstrack.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AmbulanceCall {

    public static final String STATUS_REQUESTED = "R";
    public static final String STATUS_ONGOING = "O";
    public static final String STATUS_DECLINED = "D";
    public static final String STATUS_SUSPENDED = "S";
    public static final String STATUS_COMPLETED = "C";

    private int id;
    private int ambulanceId;
    private String status;
    private Date createdAt;
    private List<Waypoint> waypointSet;
    private boolean sorted;

    public AmbulanceCall(int id, int ambulanceId, String status, Date createdAt, List<Waypoint> waypointSet) {
        this.id = id;
        this.ambulanceId = ambulanceId;
        this.status = status;
        this.createdAt = createdAt;
        this.waypointSet = waypointSet;
        this.sorted = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmbulanceId() {
        return ambulanceId;
    }

    public void setAmbulanceId(int ambulanceId) {
        this.ambulanceId = ambulanceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isSorted() {
        return sorted;
    }

    public List<Waypoint> getWaypointSet() {
        return waypointSet;
    }

    public void setWaypointSet(List<Waypoint> waypointSet) {
        this.waypointSet = waypointSet;
    }

    public void sortWaypoints() {
        Collections.sort(waypointSet, new Waypoint.SortByOrder());
        this.sorted = true;
    }

    public Waypoint getNextIncidentWaypoint() {
        return getNextWaypoint("i");
    }

    public Waypoint getNextWaypoint() {
        return getNextWaypoint(null);
    }

    public Waypoint getNextWaypoint(String type) {
        // Sort first?
        if (!isSorted())
            sortWaypoints();

        // Find first non-visited waypoint
        for (Waypoint waypoint : waypointSet) {
            if ((type == null || waypoint.getLocation().getType().equals(type)) && !waypoint.isVisited())
                return waypoint;
        }

        // Otherwise return null
        return null;
    }

    public boolean containsWaypoint(Waypoint waypoint) {
        return waypointSet.contains(waypoint);
    }

    public int getMaximumWaypointOrder() {
        // Sort first?
        if (!isSorted())
            sortWaypoints();

        // Find maximum order
        int maximumOrder = -1;
        for (Waypoint waypoint : waypointSet)
            maximumOrder = Math.max(maximumOrder, waypoint.getOrder());

        // Return maximum
        return maximumOrder;
    }

}

