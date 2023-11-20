package client;

import java.util.Stack;

class InputHandler {
    private Command command;
    private Stack<Command> commandHistory;

    public InputHandler() {
        commandHistory = new Stack<>();
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void handleInput() {
        if(command == null)
            return;
        command.execute();
        commandHistory.push(command);
    }

    public void undoLastCommand() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.pop();
            lastCommand.undo();
        }
    }
}
