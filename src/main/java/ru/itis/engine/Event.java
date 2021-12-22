package ru.itis.engine;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Getter
public class Event {
    public int type;
    //1-move
    // Если собиые пришло с сервера - id объекта, к которому применить событие
    // Если собиые отправляется на сервев - id объекта источника события
    public int objectId;
    public double[] data;

    public Event(int type, int objectId, double[] data) {
        this.type = type;
        this.objectId = objectId;
        this.data = data;
    }

    public static Event readEvent(DataInputStream dataInputStream) throws IOException {
        int type = dataInputStream.readInt();
        if(type == Event.END) { return null; }
        int objectId = dataInputStream.readInt();
        double[] buffer = new double[10];
        for(int i = 0; i < 10; i++) {
            buffer[i] = dataInputStream.readDouble();
        }
        return new Event(type, objectId, buffer);
    }

    public static void writeEvent(Event event, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(event.type);
        dataOutputStream.writeInt(event.objectId);
        for(int i = 0; i < 10; i++) {
            dataOutputStream.writeDouble(event.data[i]);
        }
    }

    public static int END = 999;
}
