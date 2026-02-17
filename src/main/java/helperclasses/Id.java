package helperclasses;

import java.util.UUID;

public class Id {
    private final UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }


}
