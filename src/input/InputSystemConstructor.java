package input;

public class InputSystemConstructor {
	public static InputSystem construct(String filename){
		InputSystem input = new InputSystem(filename);
		input.addTerminalChar('<');
		input.addTerminalChar('>');
		input.addTerminalChar('{');
		input.addTerminalChar('}');
		input.addTerminalChar('=');
		return input;
	}
}
