package com.smnet.sshmanager.command.unix.lsdashld;

import java.util.ArrayList;
import java.util.List;

public class SSHCommandLSDashLD {

    public static List<SSHCommandLSDashLDRow> parse(String output) {

        List<SSHCommandLSDashLDRow> rows = new ArrayList<>();

        String[] outputRows = output.split("\n");
        for (String row : outputRows) {

            if (row.isEmpty()) continue;

            String[] columns = row.split("\\s");

            int index = 1;
            String fullPath = columns[columns.length - index];

            while (!(fullPath.charAt(0) == '/'))
                fullPath = columns[columns.length - (++index)];

            fullPath = row.substring(row.indexOf(fullPath));

            rows.add(new SSHCommandLSDashLDRow(row.charAt(0) == 'd', fullPath, '/'));
        }

        return rows;
    }
}
