/**
 * Triggers a push event into the analytics stream.
 *
 * @export
 * @param tenant Name of the event to push
 * @param data Custom data for the action
 * @param eventAction name of the event to trigger
 */
export function trackAction(
  tenant: string,
  data: Record<string, string>,
  eventAction: string,
): void {
  console.log(
    "[Analytics] Tracking new action for '%s' and event action '%s' with:",
    tenant,
    eventAction,
    data
    )

  window.digitalData[tenant] = { ...data }
  window.digitalData.event.push({ eventAction })
}
