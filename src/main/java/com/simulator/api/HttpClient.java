package com.simulator.api;

import java.io.IOException;

public interface HttpClient {
    String get() throws IOException;
}