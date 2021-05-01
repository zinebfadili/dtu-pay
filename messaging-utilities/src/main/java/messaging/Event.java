package messaging;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Event {

    private String eventType;
    private Object[] arguments = null;
    private int statusCode = 200;
    private String message = "Ok";

    public Event() {
    }

    ;

    public Event(String eventType, Object[] arguments) {
        this.eventType = eventType;
        this.arguments = arguments;
    }

    public Event(String eventType, int statusCode, String message, Object[] arguments) {
        this.eventType = eventType;
        this.arguments = arguments;
        this.statusCode = statusCode;
        this.message = message;
    }

    public Event(String type) {
        this.eventType = type;
    }

    public String getEventType() {
        return eventType;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	/*	public static <T> T convertArgumentsToClass(T classType, Event e){
		return new Gson().fromJson(e.getArguments()[0].toString(), (Type) classType);
	}*/

    public boolean equals(Object o) {
        if (!this.getClass().equals(o.getClass())) {
            return false;
        }
        Event other = (Event) o;
        return this.eventType.equals(other.eventType) &&
                (this.getArguments() != null &&
                        this.getArguments().equals(other.getArguments())) ||
                (this.getArguments() == null && other.getArguments() == null) &&
                        (this.getStatusCode() == other.getStatusCode()) &&
                        (this.getMessage().equals(other.getMessage()));
    }

    public int hashCode() {
        return eventType.hashCode();
    }

    public String toString() {
        List<String> strs = new ArrayList<>();
        if (arguments != null) {
            List<Object> objs = Arrays.asList(arguments);
            strs = objs.stream().map(o -> o.toString()).collect(Collectors.toList());
        }

        return String.format("event(%s,%s,%s,%s)", eventType, statusCode, message, String.join(",", strs));
    }

    public static <T> T ParseMessageType(Object argument, Class<T> objectClass) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(argument);
        return gson.fromJson(jsonString, objectClass);
    }
}
