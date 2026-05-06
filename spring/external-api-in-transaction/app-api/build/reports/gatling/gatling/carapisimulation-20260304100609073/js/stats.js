var stats = {
    type: "GROUP",
name: "All Requests",
path: "",
pathFormatted: "group_missing-name--1146707516",
stats: {
    "name": "All Requests",
    "numberOfRequests": {
        "total": "50",
        "ok": "10",
        "ko": "40"
    },
    "minResponseTime": {
        "total": "30279",
        "ok": "50438",
        "ko": "30279"
    },
    "maxResponseTime": {
        "total": "50463",
        "ok": "50463",
        "ko": "30366"
    },
    "meanResponseTime": {
        "total": "34345",
        "ok": "50452",
        "ko": "30318"
    },
    "standardDeviation": {
        "total": "8054",
        "ok": "9",
        "ko": "30"
    },
    "percentiles1": {
        "total": "30339",
        "ok": "50460",
        "ko": "30302"
    },
    "percentiles2": {
        "total": "30365",
        "ok": "50460",
        "ko": "30353"
    },
    "percentiles3": {
        "total": "50460",
        "ok": "50463",
        "ko": "30366"
    },
    "percentiles4": {
        "total": "50463",
        "ok": "50463",
        "ko": "30366"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 0,
    "percentage": 0.0
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 0,
    "percentage": 0.0
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 10,
    "percentage": 20.0
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 40,
    "percentage": 80.0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "0.98",
        "ok": "0.2",
        "ko": "0.78"
    }
},
contents: {
"req_test-3556498": {
        type: "REQUEST",
        name: "test",
path: "test",
pathFormatted: "req_test-3556498",
stats: {
    "name": "test",
    "numberOfRequests": {
        "total": "50",
        "ok": "10",
        "ko": "40"
    },
    "minResponseTime": {
        "total": "30279",
        "ok": "50438",
        "ko": "30279"
    },
    "maxResponseTime": {
        "total": "50463",
        "ok": "50463",
        "ko": "30366"
    },
    "meanResponseTime": {
        "total": "34345",
        "ok": "50452",
        "ko": "30318"
    },
    "standardDeviation": {
        "total": "8054",
        "ok": "9",
        "ko": "30"
    },
    "percentiles1": {
        "total": "30339",
        "ok": "50460",
        "ko": "30302"
    },
    "percentiles2": {
        "total": "30365",
        "ok": "50460",
        "ko": "30353"
    },
    "percentiles3": {
        "total": "50460",
        "ok": "50463",
        "ko": "30366"
    },
    "percentiles4": {
        "total": "50463",
        "ok": "50463",
        "ko": "30366"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 0,
    "percentage": 0.0
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 0,
    "percentage": 0.0
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 10,
    "percentage": 20.0
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 40,
    "percentage": 80.0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "0.98",
        "ok": "0.2",
        "ko": "0.78"
    }
}
    }
}

}

function fillStats(stat){
    $("#numberOfRequests").append(stat.numberOfRequests.total);
    $("#numberOfRequestsOK").append(stat.numberOfRequests.ok);
    $("#numberOfRequestsKO").append(stat.numberOfRequests.ko);

    $("#minResponseTime").append(stat.minResponseTime.total);
    $("#minResponseTimeOK").append(stat.minResponseTime.ok);
    $("#minResponseTimeKO").append(stat.minResponseTime.ko);

    $("#maxResponseTime").append(stat.maxResponseTime.total);
    $("#maxResponseTimeOK").append(stat.maxResponseTime.ok);
    $("#maxResponseTimeKO").append(stat.maxResponseTime.ko);

    $("#meanResponseTime").append(stat.meanResponseTime.total);
    $("#meanResponseTimeOK").append(stat.meanResponseTime.ok);
    $("#meanResponseTimeKO").append(stat.meanResponseTime.ko);

    $("#standardDeviation").append(stat.standardDeviation.total);
    $("#standardDeviationOK").append(stat.standardDeviation.ok);
    $("#standardDeviationKO").append(stat.standardDeviation.ko);

    $("#percentiles1").append(stat.percentiles1.total);
    $("#percentiles1OK").append(stat.percentiles1.ok);
    $("#percentiles1KO").append(stat.percentiles1.ko);

    $("#percentiles2").append(stat.percentiles2.total);
    $("#percentiles2OK").append(stat.percentiles2.ok);
    $("#percentiles2KO").append(stat.percentiles2.ko);

    $("#percentiles3").append(stat.percentiles3.total);
    $("#percentiles3OK").append(stat.percentiles3.ok);
    $("#percentiles3KO").append(stat.percentiles3.ko);

    $("#percentiles4").append(stat.percentiles4.total);
    $("#percentiles4OK").append(stat.percentiles4.ok);
    $("#percentiles4KO").append(stat.percentiles4.ko);

    $("#meanNumberOfRequestsPerSecond").append(stat.meanNumberOfRequestsPerSecond.total);
    $("#meanNumberOfRequestsPerSecondOK").append(stat.meanNumberOfRequestsPerSecond.ok);
    $("#meanNumberOfRequestsPerSecondKO").append(stat.meanNumberOfRequestsPerSecond.ko);
}
