package com.hayanesh.count;

import java.util.List;

public class LineCount implements Count {

    @Override
    public long find(List<String> lines) {
        return lines.size();
    }
}
