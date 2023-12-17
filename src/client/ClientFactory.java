package client;

import common.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientFactory {
    private static final List<ClientEntityType> clientTypes = new ArrayList<>();

    public static ClientEntityType getClientEntityType(EntityType entityType, String moveSound) {
        Optional<ClientEntityType> typeOptional = clientTypes.stream()
                .filter(clientEntityType -> clientEntityType.entityType().equals(entityType)
                        && clientEntityType.moveSound().equals(moveSound))
                .findAny();
        if (typeOptional.isEmpty()) {
            ClientEntityType type = new ClientEntityType(entityType, moveSound);
            clientTypes.add(type);
            return type;
        }
        return typeOptional.get();

    }
}
