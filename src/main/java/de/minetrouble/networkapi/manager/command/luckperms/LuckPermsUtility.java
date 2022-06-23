package de.minetrouble.networkapi.manager.command.luckperms;

import com.google.common.collect.Lists;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.Node;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author KeinByte
 * @since 22.06.2022
 */
public class LuckPermsUtility {

    public static Optional<Node> getTemporaryGroup(User user){
        return user.data().toCollection().stream()
                .filter(node -> (node.hasExpiry() && node.getKey().toLowerCase().startsWith("group."))).findAny();
    }

    public static List<Group> getUserGroups(User user){
        List<Group> groups = Lists.newArrayList();
        List<Node> nodes = (List<Node>)user.data().toCollection().stream()
                .filter(node -> node.getKey().toLowerCase().startsWith("group.")).collect(Collectors.toList());
        for (Node node : nodes){
            Group group;
            if ((node.getKey().split("\\.")).length == 2 && (group = LuckPermsProvider.get().getGroupManager().getGroup(node.getKey().split("\\.")[1])) != null){
                groups.add(group);
            }
        }
        return groups;
    }

    public static boolean hasPermission(@NotNull UUID uuid, @NotNull String permission, boolean value) {
        User user = getLuckPermsUser(uuid);
        if (user == null)
            return false;
        return hasPermission(user, permission, value);
    }

    public static boolean hasPermission(@NotNull User user, @NotNull String permission, boolean value) {
        boolean b = user.getCachedData().getPermissionData().checkPermission(permission).asBoolean();
        return (b == value);
    }

    public static User getLuckPermsUser(@NotNull UUID uuid) {
        UserManager userManager = LuckPermsProvider.get().getUserManager();
        try {
            return userManager.isLoaded(uuid) ? userManager.getUser(uuid) : userManager.loadUser(uuid).get();
        } catch (InterruptedException|java.util.concurrent.ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void addPermission(UUID uuid, String node) {
        User user = getLuckPermsUser(uuid);
        if (user == null)
            return;
        user.data().add((Node)Node.builder(node).value(true).build());
        LuckPermsProvider.get().getUserManager().saveUser(user);
    }

    public static void removePermission(UUID uuid, String node) {
        User user = getLuckPermsUser(uuid);
        if (user == null)
            return;
        Optional<Node> any = user.data().toCollection().stream().filter(n -> (n.getKey().equalsIgnoreCase(node) && n.getValue())).findAny();
        any.ifPresent(value -> {
            user.data().remove(value);
            LuckPermsProvider.get().getUserManager().saveUser(user);
        });
    }

}
