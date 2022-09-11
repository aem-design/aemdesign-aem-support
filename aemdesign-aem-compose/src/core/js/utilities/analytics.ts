/**
 * Triggers a push event into the analytics stream.
 *
 * @export
 * @param {string} tenant Name of the event to push
 * @param {{ [key: string]: string }} data Custom data for the action
 * @param {string} eventAction name of the event to trigger
 */
export function trackAction(
  tenant: string,
  data: { [key: string]: string },
  eventAction: string,
): void {
  console.log(
    "[Analytics] Tracking new action for '%s' and event action '%s' with:",
    tenant,
    eventAction,
    data,
  )

  window.digitalData[tenant] = { ...data }
  window.digitalData.event.push({ eventAction })
}
