package simulation.utils.io.console;

import simulation.utils.AnsiColors;
import simulation.utils.io.console.messages.Message;
import simulation.utils.io.console.messages.MessageBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {

    private static BufferedReader inputReader;

    private Console() {}

    public static void init() {
        inputReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public static void close() {
        if(inputReader != null) {
            try {
                inputReader.close();
            } catch (IOException e) {
                send("[System] Fatal closing error: \n" + e.getMessage(),
                        Message.Type.ERROR);
            }
        }
    }

    public static void send(Message message) {
        System.out.print(message.toString());
    }

    public static void send(String message, Message.Type type) {
        send(new MessageBuilder().
                text(message).effects(type.getColor()).build());
    }

    public static void newLine() {
        System.out.println();
    }

    public static String receive(Message message) {
        send(message);
        try {
            return inputReader.readLine();
        } catch (IOException e) {
            send("[System] Fatal reading error: \n" + e.getMessage(),
                    Message.Type.ERROR);
        }
        return "";
    }

    public static String receive(String message) {
        return receive(new MessageBuilder().
                text(message).
                effects(AnsiColors.WHITE_BRIGHT).
                build());
    }

}
