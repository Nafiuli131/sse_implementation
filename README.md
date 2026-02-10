# sse_implementation

* implemented token streaming using SseEmitter.
* The request thread releases immediately.
* A worker thread pushes model deltas.
* We send start, delta, and end events.
* Client renders progressively.