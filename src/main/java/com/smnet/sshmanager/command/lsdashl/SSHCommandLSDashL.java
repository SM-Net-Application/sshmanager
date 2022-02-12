package com.smnet.sshmanager.command.lsdashl;

import java.util.ArrayList;
import java.util.List;

public class SSHCommandLSDashL {

    public static List<SSHCommandLSDashLRow> parse(String output) {

        List<SSHCommandLSDashLRow> rows = new ArrayList<>();

        String[] outputRows = output.split("\n");
        for (String row : outputRows) {

            if (row.isEmpty()) continue;

            String[] columns = row.split("\\s");
            rows.add(new SSHCommandLSDashLRow(row.charAt(0) == 'd', columns[columns.length - 1], '/'));
        }

        return rows;
    }
}
