package com.ege;

public class KeyPressedCommand implements UndoableCommand{
    String old_data;//command in tuttugu veri
    
    public KeyPressedCommand(String oldText) {
        old_data = oldText;
    }
    
    @Override
    public void undo() {
        //metin manager singleton ın içindeki textarea ya eski data yı koyar
        MetinManager.getInstance().textArea.setText(old_data);
    }

    @Override
    public void execute() {
        //textarea otomatik olarak text i kendine yazdıgı için execute işlemine gerek yoktur.
        //MetinManager.getInstance().textArea.setText(new_data);
    }
    
}
