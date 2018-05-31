# SubscibeEvent

* Whenever you publish an event into your bus,
 * change the code to expose anObservable.
 * Whenever you listen to an event from the bus,
 * change the codeto subscribe to the Observables you have exposed.
 * -by jakeWharton
 
 
 
 
 # use
 *implementation 'com.github.sunshaobei:SubscibeEvent:1.0.0'
 
 
 *allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
  
# whereever you want to subscribe a event :
  *//"event"  method'name of obj
 *  SubscribeEvent.getInstance().subscribe("event", obj);
   
   
   *// avoid memory leaks  （activity on destroy）
 *  SubscribeEvent.getInstance().removeuObservable("event",obj);
   # expose a event
  * //  "event" ->method'name,   args-> method param
  * SubscribeEvent.getInstance().exposeEvent("event",args...);
  
