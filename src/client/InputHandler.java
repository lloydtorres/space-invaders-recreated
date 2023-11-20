package client;

import java.util.Queue;
import java.util.Stack;

class InputHandler {
    private Command command;
    private FixedSizeQueue<Command> commandHistory;

    public InputHandler() {
        commandHistory = new FixedSizeQueue<>(50);
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void handleInput() {
        if(command == null)
            return;
        command.execute();
        commandHistory.enqueue(command);
    }

    public void undoLastCommand() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.dequeue();
            lastCommand.undo();
        }
    }
}
